import { SharedModule } from './../../shared/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { StaffRoutingModule } from './staff-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StaffComponent } from './staff.component';
import { AddupdatestaffmodalComponent } from './addupdatestaffmodal/addupdatestaffmodal.component';



@NgModule({
  declarations: [StaffComponent, AddupdatestaffmodalComponent],
  imports: [
    CommonModule,
    SharedModule,
    StaffRoutingModule,
    NgxPaginationModule
  ],
  exports: [
    AddupdatestaffmodalComponent
  ],
  entryComponents: [
    AddupdatestaffmodalComponent
  ]
})
export class StaffModule { }
