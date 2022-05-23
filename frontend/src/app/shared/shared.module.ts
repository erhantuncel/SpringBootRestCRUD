import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule, BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ConfirmationmodalComponent } from './confirmationmodal/confirmationmodal.component';


@NgModule({
  declarations: [
    ConfirmationmodalComponent
  ],
  imports: [
    CommonModule,
    ModalModule.forRoot(),
    TranslateModule
  ],
  providers: [
    BsModalRef,
    BsModalService
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    ModalModule,
    ConfirmationmodalComponent
  ],
  entryComponents: [
    ConfirmationmodalComponent
  ]
})
export class SharedModule { }
