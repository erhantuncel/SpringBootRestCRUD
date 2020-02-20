import { map } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  login(userName: string, password: string) {
    return this.http.post<any>(environment.API_BASE_PATH + '/token',
                               {userName, password}).pipe(map(user => {
      if (user && user.token) {
        localStorage.setItem('currentUser', JSON.stringify(user));
      }
      return user;
    }));
  }

  logout() {
    localStorage.removeItem('currentUser');
  }
}
