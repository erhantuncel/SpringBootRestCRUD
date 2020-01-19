import { SharedModule } from './../../shared/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { DepartmentRoutingModule } from './department-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DepartmentComponent } from './department.component';
import { AddupdatedepartmentComponent } from './addupdatedepartment/addupdatedepartment.component';


@NgModule({
  declarations: [DepartmentComponent, AddupdatedepartmentComponent],
  imports: [
    CommonModule,
    SharedModule,
    DepartmentRoutingModule,
    NgxPaginationModule,
  ],
  exports: [
    AddupdatedepartmentComponent
  ],
  entryComponents: [
    AddupdatedepartmentComponent
  ]
})
export class DepartmentModule { }
