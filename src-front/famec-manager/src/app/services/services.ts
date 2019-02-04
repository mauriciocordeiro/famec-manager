import { Observable, of } from 'rxjs';

export class Services {

    public PORT: number = 8080;
    public HOST: string = window.location.host.split(":")[0];
    public CONTEXT: string = 'famec';
    public FULL_CONTEXT: string = "http://" + this.HOST + ":" + this.PORT + "/" + this.CONTEXT;

    /**
     * Handle Http operation that failed.
     * Let the app continue.
     * @param operation - name of the operation that failed
     * @param result - optional value to return as the observable result
     */
    public handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {

            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead

            // TODO: better job of transforming error for user consumption
            this.log(`${operation} failed: ${error.message}`);

            // Let the app keep running by returning an empty result.
            return of(result as T);
        };
    }

    /** Log a HeroService message with the MessageService */
    public log(message: string) {
        // this.messageService.add(`HeroService: ${message}`);
        console.log(message);
    }

}