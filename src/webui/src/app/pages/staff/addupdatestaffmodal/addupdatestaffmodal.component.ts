import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { StaffService } from './../../../services/shared/staff.service';
import { DepartmentService } from './../../../services/shared/department.service';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-addupdatestaffmodal',
  templateUrl: './addupdatestaffmodal.component.html',
  styleUrls: ['./addupdatestaffmodal.component.css']
})
export class AddupdatestaffmodalComponent implements OnInit {

  addStaffForm: FormGroup;
  @Output() event: EventEmitter<any> = new EventEmitter<any>();

  departmentId: number;
  staffId: number;
  staffFirstName: string;
  staffLastName: string;
  staffphone: string;
  staffEmail: string;
  staffImage: SafeResourceUrl;
  staffCreateDate: Date;
  departmentName: string;

  title: string;
  acceptButtonLabel: string;

  mode: string; // update, add, detail
  departments = [];

  constructor(private bsModalRef: BsModalRef,
              private departmentService: DepartmentService,
              private staffService: StaffService,
              private domSanitize: DomSanitizer) { }

  ngOnInit() {
    this.populateDepartments();
    this.populateStaffDetails();
    this.mode = 'detail';
    this.title = 'Personel Bilgileri';
    this.acceptButtonLabel = 'Güncelle';
  }

  populateDepartments() {
    this.departmentService.getAll().subscribe(allDepartments => {
      this.departments = allDepartments.body;
    });
  }

  populateStaffDetails() {
    this.staffService.getByDepartmentIdAndStaffId(this.departmentId, this.staffId).subscribe(staffResponse => {
      this.staffFirstName = staffResponse.body.firstName;
      this.staffLastName = staffResponse.body.lastName;
      this.staffphone = staffResponse.body.phone;
      this.staffEmail = staffResponse.body.email;
      if (staffResponse.body.image) {
        this.staffImage = this.domSanitize.bypassSecurityTrustResourceUrl('data:image/png;base64, ' + staffResponse.body.image);
      }
      this.staffCreateDate = staffResponse.body.createDate;
      this.departmentName = staffResponse.body.department.departmentName;
    });
  }

  changeModeToUpdate() {
    this.mode = 'update';
  }

  onSubmitAddDepartment(staffValue) {
    this.event.emit('OK');
    this.closeModal();
  }

  onClickCancel() {
    this.event.emit('Cancel');
    this.closeModal();
  }

  closeModal() {
    this.bsModalRef.hide();
  }
}
