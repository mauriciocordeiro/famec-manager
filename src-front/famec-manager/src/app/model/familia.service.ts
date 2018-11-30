import { Injectable } from '@angular/core';

import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';
import { Familia } from './familia';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
const apiUrl = "http://localhost:8080/famec/rws/familia";
@Injectable({
	providedIn: 'root'
})
export class FamiliaService {

	constructor(private http: HttpClient) { }

	private handleError<T>(operation = 'operation', result?: T) {
		return (error: any): Observable<T> => {

			// TODO: send the error to remote logging infrastructure
			console.error(error); // log to console instead

			// Let the app keep running by returning an empty result.
			return of(result as T);
		};
	}

	/**
	 * CRUD
	 */
	getFamilia(): Observable<Familia[]> {
		
		return this.http.get<Familia[]>(apiUrl+'/get')
			.pipe(
				tap(heroes => console.log('fetched familia')),
				catchError(this.handleError('getFamilia', []))
			);
	}



	public test(): Observable<String[]> {
		debugger;
		return this.http.get<String[]>(apiUrl+'/test')
		.pipe(
			tap(heroes => console.log('fetched familia')),
			catchError(this.handleError('getFamilia', []))
		);
	}
}
