import { LocalStorage } from './LocalStorage';
import { Router } from '@angular/router';
import SimpleCrypto from "simple-crypto-js";
import { MatSnackBar } from '@angular/material';

export class Utils {

  
  // snackbar message type
  static SNACK_ALERT: string = 'alert';
  static SNACK_ERROR: string = 'error';
  static SNACK_SUCCESS: string = 'success';

  static CYPHER:string = 'famec-manager';

  constructor() { }

  static abreviarNome(nome: string): string {
    let parts = nome.split(' ');
    if (parts.length > 1)
      return parts[0] + ' ' + parts[1].substring(0, 1);
    else
      return nome;
  }

  public static objToCamelCase(o): object {
    try {
      let obj2 = {};

      Object.keys(o).forEach((key) => {
        let value = o[key];
        key = key.toLowerCase();
        obj2[this.toCamelCase(key)] = value;
      });

      return obj2;
    } catch (e) {
      console.log(e);
    }
  }

  public static toCamelCase(str) {
    var arr = str.split(/[_-]/);
    var newStr = "";
    for (var i = 1; i < arr.length; i++) {
      newStr += arr[i].charAt(0).toUpperCase() + arr[i].slice(1);
    }
    return arr[0] + newStr;
  }

  public static objToUnderscore(o): object {
    try {
      let obj2 = {};

      Object.keys(o).forEach((key) => {
        let value = o[key];
        key = key.toLowerCase();
        obj2[this.toCamelCase(key)] = value;
      });

      return obj2;
    } catch (e) {
      console.log(e);
    }
  }

  static isJSON(value: any): boolean {
    try {
      let isParsable = JSON.parse(value) || false;
      return isParsable;
    } catch (e) {
      return false;
    }
  }

  static reloadBrowser(): void {
    window.location.reload()
  }

  static getRequest(url: string): Promise<any> {
    return new Promise<any>(
      function (resolve, reject) {
        const request = new XMLHttpRequest();
        request.onload = function () {
          if (request.status === 200) {
            resolve(request.response);
          }
          else {
            reject(new Error(request.statusText));
          }
        };
        request.onerror = function () {
          reject(new Error('XMLHttpRequest Error: ' + request.statusText));
        };
        request.open('GET', url);
        request.send();
      });
  }

  static getDateTime(date: any) {
    if (!date)
      return null;

    if (typeof (date) === 'number') {
      return new Date(date * 1000);
    }

    if (date.substring(2, 3) === '/' && date.substring(5, 6) === '/') {
      let year = date.substring(6, 10);
      let month = date.substring(3, 5);
      let day = date.substring(0, 2);
      return new Date(Date.parse(year + '-' + month + '-' + day + 'T03:00:00Z')).getTime();
    } else if (date.substring(4, 5) === '-' && date.substring(7, 8) === '-') {
      return new Date(date).getTime();
    }

    return null;
  }

  static getDataAtualStr() {
    let date = new Date();
    let dateStr = date.getDate() + "/" + date.getMonth() + "/" + date.getFullYear();
    return dateStr;
  }

  static getIdade(dtNascimento, diaReferencia, mesReferencia, anoReferencia) {

    if (typeof (dtNascimento) == "string") {
      dtNascimento = new Date(parseInt(dtNascimento.substring(0, 4)), (parseInt(dtNascimento.substring(5, 7)) - 1), parseInt(dtNascimento.substring(8, 10)));
    }

    let dtHoje = new Date();

    if (diaReferencia > 0)
      dtHoje.setDate(diaReferencia);
    if (mesReferencia > 0)
      dtHoje.setMonth(mesReferencia);
    if (anoReferencia > 0)
      dtHoje.setFullYear(anoReferencia);

    let idade = dtHoje.getFullYear() - dtNascimento.getFullYear();

    if (dtNascimento.getMonth() > dtHoje.getMonth()) {
      idade--;
    }
    else if (dtNascimento.getMonth() == dtHoje.getMonth()) {
      if (dtNascimento.getDate() > dtHoje.getDate()) {
        idade--;
      }
    }

    return idade;
  }

  static zeroFill(num: number, numZeros: number) {
    var n = Math.abs(num);
    var zeros = Math.max(0, numZeros - Math.floor(n).toString().length);
    var zeroString = Math.pow(10, zeros).toString().substr(1);
    if (num < 0) {
      zeroString = '-' + zeroString;
    }

    return zeroString + n;
  }

  /**
   * json-stringify-safe
   * Like JSON.stringify, but doesn't blow up on circular refs.
   * 
   * @version 5.0.1
   * @author Isaac Z. Schlueter <i@izs.me> (http://blog.izs.me)
   * contributors: Andri Möll <andri@dot.ee> (http://themoll.com)
   * 
   * @license ISC
   * @homepage https://github.com/isaacs/json-stringify-safe
   * @param obj 
   * @param replacer 
   * @param spaces 
   * @param cycleReplacer 
   */

  static stringify(obj, replacer, spaces, cycleReplacer?) {
    return JSON.stringify(obj, Utils.serializer(replacer, cycleReplacer), spaces);
  }

  static serializer(replacer, cycleReplacer) {
    var stack = [], keys = []

    if (cycleReplacer == null) cycleReplacer = function (key, value) {
      if (stack[0] === value) return "[Circular ~]"
      return "[Circular ~." + keys.slice(0, stack.indexOf(value)).join(".") + "]"
    }

    return function (key, value) {
      if (stack.length > 0) {
        var thisPos = stack.indexOf(this)
        ~thisPos ? stack.splice(thisPos + 1) : stack.push(this)
        ~thisPos ? keys.splice(thisPos, Infinity, key) : keys.push(key)
        if (~stack.indexOf(value)) value = cycleReplacer.call(this, key, value)
      }
      else stack.push(value)

      return replacer == null ? value : replacer.call(this, key, value)
    }
  }  

  static getAge(date:Date):any {
    try {
      let timeDiff = Math.abs(Date.now() - date.getTime());
      let age = Math.floor((timeDiff / (1000 * 3600 * 24))/365.25);
      return age;
    } catch(e) {
      return 0;
    }
  }

  /** ============================================================
   * SimpleCrypto
   * 
   * @description Simplified AES crypthography for safe 
   * and easier encryption and decryption proccesses of 
   * any JavaScript objects
   * 
   * @see https://github.com/danang-id/simple-crypto-js
   * @author danang-id
   * @version v2.0.2
   * @license MIT
   */
  //
  static encrypt(source: string): string {
    let crypter = new SimpleCrypto(this.CYPHER);
    return crypter.encrypt(source);
  }
  //
  static decrypt(source: string): string | object {
    let crypter = new SimpleCrypto(this.CYPHER);
    return crypter.decrypt(source);
  }
  //   ============================================================
}
