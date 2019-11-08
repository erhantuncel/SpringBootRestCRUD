import { SharedModule } from './../../shared/shared.module';
import { StaffRoutingModule } from './staff-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StaffComponent } from './staff.component';



@NgModule({
  declarations: [StaffComponent],
  imports: [
    CommonModule,
    SharedModule,
    StaffRoutingModule
  ]
})
export class StaffModule { }
