import { Injectable } from '@angular/core';

@Injectable()
export class Result {

  code:number = void(0);
  message:string = void(0);
  objects:any = {};
  detail:string = void(0);

  constructor(code?:number,
              message?:string,
              key?:string,
              value?:any) {
    this.code = code;
    this.message = message;

    if(key)
        this.objects[key] = value;
  }

  addObject(key:string, object:any):void {
    this.objects[key] = object;
  }

  getObject(key:string):any{
    for(let keyObject in this.objects){
      if(key === keyObject){
        return this.objects[key];
      }
    }

    return null;
  }

  getCode():number{
    return this.code;
  }

  setCode(code:number):void{
    this.code = code;
  }

  getMessage():string{
    return this.message;
  }

  setMessage(message:string):void{
    this.message = message;
  }

  getObjects():any[]{
    return this.objects;
  }

  setObjects(objects:any[]):void {
    this.objects = objects;
  }

  getDetail():string{
    return this.detail;
  }

  setDetail(detail:string):void{
    this.detail = detail;
  }

}
