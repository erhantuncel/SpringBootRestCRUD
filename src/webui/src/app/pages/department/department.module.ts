import { SharedModule } from './../../shared/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { DepartmentRoutingModule } from './department-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DepartmentComponent } from './department.component';


@NgModule({
  declarations: [DepartmentComponent],
  imports: [
    CommonModule,
    SharedModule,
    DepartmentRoutingModule,
    NgxPaginationModule
  ]
})
export class DepartmentModule { }
