import { Injectable } from '@angular/core';
import { Utils } from './Utils';

@Injectable()
export class SessionStorage {

    static sessionStorage: Storage = window.sessionStorage;

    constructor() { }

    static put(name:string, value:any):void {
        value = Utils.isJSON(value) ? JSON.stringify(value) : value;
        this.sessionStorage.setItem(name, value);
    }

    static get(name:any):any {
        let s = this.sessionStorage.getItem(name);
        return Utils.isJSON(s) ? JSON.parse(s) : s;
    }

    static remove(name:any):any {
        let s = this.sessionStorage.removeItem(name);
    }


}