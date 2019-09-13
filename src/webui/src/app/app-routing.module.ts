import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [{
  path: '',
  children:  [
    {path: '', pathMatch: 'full', redirectTo: 'staff'},
    {path: 'department', loadChildren: './pages/department/department.module#DepartmentModule'},
    {path: 'staff', loadChildren: './pages/staff/staff.module#StaffModule'}
  ]
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
