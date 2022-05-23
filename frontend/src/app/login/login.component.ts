import { TranslateService } from '@ngx-translate/core';
import { AuthenticationService } from './../security/authentication.service';
import { ToastrService } from 'ngx-toastr';
import { User } from './../common/user';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @ViewChild('loginForm', {static: true}) public loginForm: NgForm;
  user: User;
  returnUrl: string;

  constructor(private toastr: ToastrService,
              private route: ActivatedRoute,
              private router: Router,
              private authenticationService: AuthenticationService,
              private translate: TranslateService) { }

  ngOnInit() {
    this.user = new User();
    this.authenticationService.logout();
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  login() {
    console.log(this.user);
    if (this.loginForm.invalid) {
      this.translate.get('LOGIN.toastr.error.not.empty').subscribe(errorResponse => {
        this.toastr.error(errorResponse);
      });
      return;
    }

    this.authenticationService.login(this.user.userName, this.user.password).pipe(first()).subscribe(
      data => {
        this.router.navigate([this.returnUrl]);
      },
      error => {
        if (error.error.type === 'BadCredentialsException') {
          this.translate.get('LOGIN.toastr.error.bad.credentials').subscribe(errorResponse => {
            this.toastr.error(errorResponse);
          });
        }
      }
    );

  }

}
