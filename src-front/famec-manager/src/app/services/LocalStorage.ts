import { Injectable } from '@angular/core';
import { Utils } from './Utils';

@Injectable()
export class LocalStorage {

    static localStorage:Storage = window.localStorage;

    constructor() { }

    static put(name:string, value:any):void {
        value = Utils.isJSON(value) ? JSON.stringify(value) : value;
        this.localStorage.setItem(name, value);
    }

    static get(name:any):any {
        let s = this.localStorage.getItem(name);
        return Utils.isJSON(s) ? JSON.parse(s) : s;
    }

    static remove(name:any):any {
        let s = this.localStorage.removeItem(name);
    }

}
