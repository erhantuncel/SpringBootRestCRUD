import { ApiService } from './../api.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  private DEPARTMENT_PATH = '/departments';

  constructor(private apiService: ApiService) { }

  create(department): Observable<any> {
    return this.apiService.post(this.DEPARTMENT_PATH, department).pipe(map(
      response => {
        if (response) {
          return response;
        } else {
          console.log(response);
          return {};
        }
      }
    ));
  }

  getAll(): Observable<any> {
    return this.apiService.get(this.DEPARTMENT_PATH).pipe(map(
      response => {
        if (response) {
          return response;
        } else {
          return {};
        }
      }
    ));
  }

  getWithPagination(parameters): Observable<any> {
    return this.apiService.get(this.DEPARTMENT_PATH + '/pagination', parameters).pipe(map(
      response => {
        if (response) {
          return response;
        } else {
          console.log(response);
          return {};
        }
      }
    ));
  }


  getById(id): Observable<any> {
    return this.apiService.get(this.DEPARTMENT_PATH + '/' + id).pipe(map(
      response => {
        if (response) {
          return response;
        } else {
          console.log(response);
          return {};
        }
      }
    ));
  }

  delete(id): Observable<any> {
    return this.apiService.delete(this.DEPARTMENT_PATH + '/' + id).pipe(map(
      response => {
        if (response === null) {
          return true;
        } else {
          console.log(response);
          return false;
        }
      }
    ));
  }

  update(id, department): Observable<any> {
    return this.apiService.put(this.DEPARTMENT_PATH + '/' + id, department).pipe(map(
      response => {
        if (response) {
          return response;
        } else {
          console.log(response);
          return {};
        }
      }
    ));
  }
}
