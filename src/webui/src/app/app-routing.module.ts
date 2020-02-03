import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppLayoutComponent } from './layout';


const routes: Routes = [{
  path: '', component: AppLayoutComponent,
  children:  [
    {path: '', pathMatch: 'full', redirectTo: 'department'},
    {path: 'department', loadChildren: './pages/department/department.module#DepartmentModule'},
    {path: 'staff', loadChildren: './pages/staff/staff.module#StaffModule'}
  ]
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
