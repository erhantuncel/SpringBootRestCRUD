import { environment } from './../../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.get(
      environment.API_BASE_PATH + path,
      {observe: 'response', params}
    ).pipe(catchError(this.formatError));
  }

  post(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.post(
      environment.API_BASE_PATH + path,
      JSON.stringify(params),
      this.httpOptions
    ).pipe(catchError(this.formatError));
  }

  put(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.put(
      environment.API_BASE_PATH + path,
      JSON.stringify(params),
      this.httpOptions,
    ).pipe(catchError(this.formatError));
  }

  putWithFormData(path: string, data: FormData): Observable<any> {
    return this.http.put(
      environment.API_BASE_PATH + path,
      data,
      {observe: 'response'}
    ).pipe(catchError(this.formatError));
  }

  delete(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.delete(
      environment.API_BASE_PATH + path,
      {observe: 'response', params}
    ).pipe(catchError(this.formatError));
  }

  private formatError(error: any) {
    return of({type: error.error.type, cause: error.error.cause});
  }
}
