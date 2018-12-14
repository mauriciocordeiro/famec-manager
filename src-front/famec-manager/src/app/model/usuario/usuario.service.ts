import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Usuario } from './usuario';
import { Observable, of } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
const apiUrl = "http://localhost:8080/famec/rws/usuario";

@Injectable({
	providedIn: 'root'
})
export class UsuarioService {

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
	getAll():Observable<Usuario[]> {

		return this.http.get<Usuario[]>(apiUrl+'/getAll')
			.pipe(
				tap(heroes => console.log('fetched usuario')),
				catchError(this.handleError('getAll', []))
			);

	}
}
