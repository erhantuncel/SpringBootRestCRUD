import { AuthGuard } from './security/auth.guard';
import { LoginComponent } from './login/login.component';
import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppLayoutComponent } from './layout';


const routes: Routes = [{
  path: '', component: AppLayoutComponent, canActivate: [AuthGuard],
  children:  [
    {path: '', pathMatch: 'full', redirectTo: 'department'},
    {path: 'department', loadChildren: './pages/department/department.module#DepartmentModule'},
    {path: 'staff', loadChildren: './pages/staff/staff.module#StaffModule'}
  ]
},
{ path:  'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
