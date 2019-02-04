import { Injectable } from '@angular/core';

// import { Utils } from 'sol/utils/Utils';

@Injectable()
export class ObjectUtils {

    constructor() { }

    static getInstanceByString(strJSON: any, className: Function): any {
        let obj: any = Object.create(className.prototype);
        let json: JSON = typeof strJSON === 'string' ? JSON.parse(strJSON) : strJSON;

        for (let key in json) {
            if (key.toLowerCase().substring(0, 2) === 'dt' && (json[key] instanceof String)) {
                obj[key] = this.getDateTime(obj[key]);
                continue;
            }
            obj[key] = json[key];
        }

        return obj;
    }

    static getInstanceByRegister(register: any, className: Function, parent: boolean = false): any {
        let obj: any = Object.create(className.prototype);
        let keys: string[] = ObjectUtils.describe(className, parent);
        for (let key in keys) {
            let value = register[ObjectUtils.camelCaseToUnderscore(keys[key])];
            if (keys[key].toLowerCase().substring(0, 2) === 'dt' && (value instanceof String)) {
                obj[keys[key]] = this.getDateTime(value);
                continue;
            }
            else if (keys[key].substring(0, 2) === 'vl') {
                if (typeof (register[ObjectUtils.camelCaseToUnderscore(keys[key])]) == 'string')
                    obj[keys[key]] = register[ObjectUtils.camelCaseToUnderscore(keys[key])].replace(',', '.');
                else {
                    obj[keys[key]] = register[ObjectUtils.camelCaseToUnderscore(keys[key])];
                    if(obj[keys[key]] == null)
                        obj[keys[key]] = 0;
                }
                continue;
            }
            obj[keys[key]] = ((register[ObjectUtils.camelCaseToUnderscore(keys[key])] != undefined && register[ObjectUtils.camelCaseToUnderscore(keys[key])] != '' && register[ObjectUtils.camelCaseToUnderscore(keys[key])] != 'null' && register[ObjectUtils.camelCaseToUnderscore(keys[key])] != null) || register[ObjectUtils.camelCaseToUnderscore(keys[key])] == 0 ? register[ObjectUtils.camelCaseToUnderscore(keys[key])] : null);

        }
        return obj;
    }

    static getInstanceByRestData(register: any, className: Function, parent = false): any {
        let obj: any = Object.create(className.prototype);
        let keys: string[] = ObjectUtils.describe(className, parent);

        for (let key in keys) {

            obj[keys[key]] = register[keys[key]];
        }

        return obj;
    }

    static getRegisterByInstance(obj: any, parent = false): object {
        let register: object = {};
        let keys: string[] = ObjectUtils.describe(obj.constructor, parent);

        for (let key in keys) {
            let value = register[ObjectUtils.camelCaseToUnderscore(keys[key])];
            if (key.toLowerCase().substring(0, 2) === 'dt' && (value instanceof String)) {
                register[ObjectUtils.camelCaseToUnderscore(keys[key])] = this.getDateTime(obj[keys[key]]);
                continue;
            }

            register[ObjectUtils.camelCaseToUnderscore(keys[key])] = obj[keys[key]];
        }

        return register;
    }

    static getRegisterByClass(className: Function): any {
        let obj: any = Object.create(className.prototype);
        return this.getRegisterByInstance(obj);
    }

    static camelCaseToUnderscore(input: string): string {
        let result: string = "";

        for (let i = 0; i < input.length; i++) {
            let c: string = input.charAt(i);
            result += (ObjectUtils.isUpperCase(c) ? "_" : "") + c.toLowerCase();
        }

        return result.toUpperCase();
    }

    static isUpperCase(c: string): boolean {
        return c == c.toUpperCase();
    }

    static describe(val: Function, parent = false): string[] {
        let fRegEx = new RegExp(/(?:this\.)(.+?(?= ))/g);
        let result = [];

        if (parent) {
            let proto = Object.getPrototypeOf(val.prototype);
            if (proto) {
                result = result.concat(this.describe(proto.constructor, parent));
            }
        }

        result = result.concat(val.toString().match(fRegEx) || []);


        for (let key in result) {
            result[key] = result[key].replace("this.", "").replace("=void", "");
        }

        return result;
    }

    static getDateTime(date:any){
        if(!date)
            return null;

        if(typeof(date) === 'number'){
            return new Date(date*1000);
        }

        if(date.substring(2, 3) === '/' && date.substring(5, 6) === '/'){
            let year  = date.substring(6, 10);
            let month = date.substring(3, 5);
            let day   = date.substring(0, 2);
            return new Date(Date.parse(year + '-' + month + '-' + day + 'T03:00:00Z')).getTime();
        } else if (date.substring(4, 5) === '-' && date.substring(7, 8) === '-'){
            return new Date(date).getTime();
        }

        return null;
    }
}
