import { Injectable } from '@angular/core';

@Injectable()
export class ArrayUtils {

    public static ASC:number  = 0;
    public static DESC:number = 1;

    /**
    * @author Edgard Hufelande <edgard@tivic.com.br>
    * @description Ordenar valores de um Array
    * @param va : Valor atual de um array
    * @param pv : Próximo valor de um valor atual de um array
    * @since 2017-06-29
    */
    static sort(array:Array<any>, field:string, order:number=ArrayUtils.ASC):Promise<any[]> {
        return new Promise<any[]>((resolve) => {
            resolve(array.sort((va:any, pv:any) => {

                if (va[field] > pv[field]) {
                    return order === ArrayUtils.ASC ? 1 : -1;
                }

                if (va[field] < pv[field]) {
                    return order === ArrayUtils.ASC ? -1 : 1;
                }

                return 0;
            }));
        });
    }

    /**
    * @author Edgard Hufelande <edgard@tivic.com.br>
    * @description Retornar o índice de um object dentro de um array.
    * @param array : Array que contenha o object a ser buscado
    * @param object : Object que deverá ser retornado o index
    * @since 2017-10-04
    */
    static getObjectIndex(array:Array<any>, object:any):number {
        return array.findIndex((item:any) => item == object);
    }
}
