import { TranslateService } from '@ngx-translate/core';
import { DepartmentService } from './../../../services/shared/department.service';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-addupdatedepartment',
  templateUrl: './addupdatedepartment.component.html',
  styleUrls: ['./addupdatedepartment.component.css']
})
export class AddupdatedepartmentComponent implements OnInit {

  addDepartmentForm: FormGroup;
  @Output() event: EventEmitter<any> = new EventEmitter<any>();

  departmentId: number;
  departmentNameStatus: string;
  title: string;
  buttonLabel: string;
  departmentNameMinLength = 3;
  departmentNameMaxLength = 100;


  constructor(private departmentService: DepartmentService,
              private translate: TranslateService,
              private toastr: ToastrService,
              private bsModalRef: BsModalRef,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.addDepartmentForm = this.formBuilder.group({
      departmentName: new FormControl(this.departmentNameStatus,
              [Validators.required,
              Validators.minLength(this.departmentNameMinLength),
              Validators.maxLength(this.departmentNameMaxLength)])
    });
    if (this.departmentId != null) {
      this.addDepartmentForm.addControl('idControl', new FormControl({value: this.departmentId, disabled: true}));
    }
    this.translate.get('DEPARTMENT.ADDUPDATEDEPARTMENTMODAL.title.' + (this.departmentId != null ? 'update' : 'add'))
                  .subscribe(titleResponse => {
      this.title = titleResponse;
    });
    this.translate.get('DEPARTMENT.ADDUPDATEDEPARTMENTMODAL.button.' + (this.departmentId != null ? 'update' : 'add'))
                  .subscribe(buttonLabelResponse => {
      this.buttonLabel = buttonLabelResponse;
    });
  }

  get departmentName() {
    return this.addDepartmentForm.get('departmentName');
  }

  onSubmitAddDepartment(departmentValue) {
    if (!this.addDepartmentForm.valid) {
      console.log('onSubmitAddDepartment - addDepartmentForm is not valid.');
      return;
    }
    if (this.departmentId != null) {
      this.departmentService.update(this.departmentId, departmentValue).subscribe(
        response => {
          if (response.id != null && response.departmentName === this.departmentName.value) {
            this.event.emit('OK');
            this.closeModal();
            this.translate.get('DEPARTMENT.ADDUPDATEDEPARTMENTMODAL.toastr.updateDepartment.success.message',
                                {departmentId: this.departmentId, departmentName: this.departmentName.value})
                          .subscribe(successResponse => {
                this.toastr.success(successResponse);
              });
          } else {
            this.translate.get('DEPARTMENT.ADDUPDATEDEPARTMENTMODAL.toastr.updateDepartment.error.message',
                                {departmentId: this.departmentId, departmentName: this.departmentName.value})
                              .subscribe(dangerResponse => {
              this.toastr.error(dangerResponse);
            });
            this.closeModal();
          }
        }
      );
    } else {
      this.departmentService.create(departmentValue).subscribe(
        response => {
          if (response.id != null && response.departmentName === this.departmentName.value) {
            this.event.emit('OK');
            this.closeModal();
            this.translate.get('DEPARTMENT.ADDUPDATEDEPARTMENTMODAL.toastr.addDepartment.success.message',
                                {departmentName: this.departmentName.value}).subscribe(successResponse => {
                this.toastr.success(successResponse);
              });
          } else {
            this.closeModal();
            this.translate.get('DEPARTMENT.ADDUPDATEDEPARTMENTMODAL.toastr.addDepartment.error.message',
                                {departmentId: this.departmentId, departmentName: this.departmentName.value}).subscribe(dangerResponse => {
              this.toastr.error(dangerResponse);
            });
          }
        }
      );
    }
  }

  onClickCancel() {
    this.event.emit('Cancel');
    this.closeModal();
  }

  closeModal() {
    this.bsModalRef.hide();
  }

}
