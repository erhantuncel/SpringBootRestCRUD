import { Observable } from 'rxjs';
import { ApiService } from './../api.service';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class StaffService {

  private DEPARTMENT_PATH = '/departments';
  private STAFF_PATH = '/staffs';


  constructor(private apiService: ApiService) { }

  getAllWithDepartmentIdPaginated(departmentId: number, parameters): Observable<any> {
    return this.apiService.get(this.DEPARTMENT_PATH + '/' + departmentId +
                               this.STAFF_PATH + '/pagination', parameters).pipe(map(
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

  getByDepartmentIdAndStaffId(departmentId: number, staffId: number): Observable<any> {
    return this.apiService.get(this.DEPARTMENT_PATH + '/' + departmentId +
                               this.STAFF_PATH + '/' + staffId).pipe(map(
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

  update(depId: number, staffId: number, data: FormData): Observable<any> {
    return this.apiService.putWithFormData(this.DEPARTMENT_PATH + '/' + depId +
                               this.STAFF_PATH + '/' + staffId, data).pipe(map(
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
