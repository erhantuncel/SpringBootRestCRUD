import { TranslateService } from '@ngx-translate/core';
import { DepartmentService } from './../../services/shared/department.service';
import { Component, OnInit, Input, TemplateRef } from '@angular/core';
import { PaginationInstance } from 'ngx-pagination';
import { ToastrService } from 'ngx-toastr';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css']
})
export class DepartmentComponent implements OnInit {

  data = [];
  pageSize = '5';
  pageSizeOptions = ['5', '10', '15'];

  addDepartmentModal: BsModalRef;
  addDepartmentForm;
  departmentNameMinLength = 3;
  departmentNameMaxLength = 100;

  @Input() pageSizeSelect: string;

  public config: PaginationInstance = {
    itemsPerPage: 5,
    currentPage: 1,
    totalItems: 0
  };

  addDepartmentModalConfig = {
    backdrop: true,
    ignoreBackdropClick: true
  };

  constructor(private departmentService: DepartmentService,
              private translate: TranslateService,
              private toastr: ToastrService,
              private modalService: BsModalService,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.config.itemsPerPage = Number(this.pageSize);
    this.getPage(1);
    this.addDepartmentForm = this.formBuilder.group({
      departmentName: new FormControl('',
          [Validators.required,
           Validators.minLength(this.departmentNameMinLength),
           Validators.maxLength(this.departmentNameMaxLength)])
    });
  }

  getPage(page: number) {
    this.departmentService.getWithPagination({page: page - 1, size: this.config.itemsPerPage}).subscribe(response => {
      console.log(response);
      this.data = response.content;
      this.config.currentPage = page;
      this.config.totalItems = response.totalElements;
    });
  }

  onPageSizeSelected(selectedValue: any) {
    this.config.itemsPerPage = selectedValue;
    this.getPage(1);
  }

  showAddDepartmentDialog(template: TemplateRef<any>) {
    console.log('DepartmentComponent.ts - showAddDepartmentDialog method');
    this.addDepartmentForm.reset();
    this.addDepartmentModal = this.modalService.show(template, this.addDepartmentModalConfig);
  }

  get departmentName() {
    return this.addDepartmentForm.get('departmentName');
  }

  onSubmitAddDepartment(departmentValue) {
    if (!this.addDepartmentForm.valid) {
      console.log('onSubmitAddDepartment - addDepartmentForm is not valid.');
      return;
    }
    console.log('onSubmitAddDepartment - departmentValue = ', departmentValue);
    this.departmentService.create(this.addDepartmentForm.value).subscribe(
      response => {
        if (response.id != null && response.departmentName === this.departmentName.value) {
          this.getPage(1);
          this.closeAndResetModal();
          this.translate.get('DEPARTMENT.ADDDEPARTMENTMODAL.toastr.addDepartment.success.message').subscribe(successResponse => {
              this.toastr.success(successResponse);
            });
        } else {
          this.translate.get('DEPARTMENT.ADDDEPARTMENTMODAL.toastr.addDepartment.error.message').subscribe(dangerResponse => {
            this.toastr.error(dangerResponse);
          });
          this.closeAndResetModal();
        }
      }
    );
  }

  closeAndResetModal() {
    this.addDepartmentModal.hide();
  }
}
