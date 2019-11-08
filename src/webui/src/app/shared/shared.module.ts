import { FormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
  ],
  exports: [
    FormsModule,
    TranslateModule
  ]
})
export class SharedModule { }
