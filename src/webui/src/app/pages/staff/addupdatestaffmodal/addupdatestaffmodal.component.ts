import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { Department } from './../../../common/department';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { StaffService } from './../../../services/shared/staff.service';
import { Staff } from './../../../common/staff';
import { DepartmentService } from './../../../services/shared/department.service';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-addupdatestaffmodal',
  templateUrl: './addupdatestaffmodal.component.html',
  styleUrls: ['./addupdatestaffmodal.component.css']
})
export class AddupdatestaffmodalComponent implements OnInit {

  @Output() event: EventEmitter<any> = new EventEmitter<any>();

  departmentId: number;
  staffId: number;
  staff: Staff;
  staffImageForSrc: SafeResourceUrl;
  staffImageFile: File;

  imageErrorMessage: string;
  acceptedImageTypes = ['image/png', 'image/jpeg'];

  mode: string; // update, add, detail
  departments = [];

  formData: FormData;

  constructor(private bsModalRef: BsModalRef,
              private departmentService: DepartmentService,
              private staffService: StaffService,
              private domSanitize: DomSanitizer,
              private toastr: ToastrService,
              private translate: TranslateService) { }

  ngOnInit() {
    this.staff = new Staff();
    this.staff.department = new Department();
    this.staff.department.id = this.departmentId;
    this.populateDepartments();
    console.log('staffId = ', this.staffId);
    if ( this.staffId !== undefined ) {
      this.staff.id = this.staffId;
      this.populateStaffDetails();
      this.mode = 'detail';
    } else {
      this.mode = 'add';
    }
  }

  populateDepartments() {
    this.departmentService.getAll().subscribe(allDepartments => {
      this.departments = allDepartments.body;
    });
  }

  populateStaffDetails() {
    this.staffService.getByDepartmentIdAndStaffId(this.staff.department.id, this.staff.id).subscribe(staffResponse => {
      this.staff.id = staffResponse.body.id;
      this.staff.firstName = staffResponse.body.firstName;
      this.staff.lastName = staffResponse.body.lastName;
      this.staff.phone = staffResponse.body.phone;
      this.staff.email = staffResponse.body.email;
      this.staff.createDate = staffResponse.body.createDate;
      if (staffResponse.body.image) {
        this.staff.image = staffResponse.body.image;
        this.staffImageForSrc = this.createSafeResourceUrlForImage(this.staff.image);
      }
      this.staff.department.departmentName = staffResponse.body.department.departmentName;
    });
  }

  changeModeToUpdate() {
    this.mode = 'update';
  }

  onFileChanged(event) {
    this.staffImageFile = event.target.files[0] as File;
    if (this.staffImageFile.size > 100000) {
      this.translate.get('STAFF.ADDUPDATEDETAILMODAL.image.validation.error.fileSize', {fileSize: '100 Kb'}).subscribe(
        fileSizeValidationError => {
          this.imageErrorMessage = fileSizeValidationError;
        });
      return;
    }
    if (this.acceptedImageTypes.indexOf(this.staffImageFile.type) < 0) {
      this.translate.get('STAFF.ADDUPDATEDETAILMODAL.image.validation.error.fileType', {fileTypes: 'Png, Jpg'}).subscribe(
        fileTypeValidationError => {
          this.imageErrorMessage = fileTypeValidationError;
        });
      return;
    }
    const reader = new FileReader();
    reader.onload = (onloadEvent: any) => {
      const imageBase64Path = onloadEvent.target.result.substr(onloadEvent.target.result.indexOf(',') + 1);
      this.staffImageForSrc = this.createSafeResourceUrlForImage(imageBase64Path);
    };
    reader.readAsDataURL(this.staffImageFile);
    this.imageErrorMessage = null;
  }

  onSubmitAddUpdateStaffForm() {
    this.formData = new FormData();
    this.formData.append('staff', new Blob([JSON.stringify(this.staff)], {type: 'application/json'}));
    if (this.staffImageFile != null) {
      this.formData.append('image', this.staffImageFile);
    }
    if (this.staffId != null) {
      this.staffService.update(this.departmentId, this.staff.id, this.formData).subscribe(
        updateResponse => {
          if (updateResponse.status === 200) {
            console.log(updateResponse.body);
            this.event.emit('Updated');
            this.translate.get('STAFF.ADDUPDATEDETAILMODAL.toastr.updateStaff.success.message',
                               {firstName: this.staff.firstName, lastName: this.staff.lastName}).subscribe(
              updateSuccessMessage => {
              this.toastr.success(updateSuccessMessage);
            });
          } else {
            this.translate.get('STAFF.ADDUPDATEDETAILMODAL.toastr.updateStaff.error.message',
                               {firstName: this.staff.firstName, lastName: this.staff.lastName}).subscribe(
              updateErrorMessage => {
              this.toastr.error(updateErrorMessage);
            });
          }
        });
    } else {
      console.log(this.staff);
      this.staffService.create(this.staff.department.id, this.formData).subscribe(addResponse => {
        console.log(addResponse);
        if (addResponse.status === 200) {
          this.event.emit('Added');
          this.translate.get('STAFF.ADDUPDATEDETAILMODAL.toastr.addStaff.success.message',
                             {firstName: this.staff.firstName, lastName: this.staff.lastName}).subscribe(
            addSuccessMessage => {
            this.toastr.success(addSuccessMessage);
          });
        } else {
          this.translate.get('STAFF.ADDUPDATEDETAILMODAL.toastr.addStaff.error.message',
                             {firstName: this.staff.firstName, lastName: this.staff.lastName}).subscribe(
            addErrorMessage => {
            this.toastr.error(addErrorMessage);
          });
        }
      });
    }
    this.closeModal();
  }

  onClickRemoveImage() {
    this.staff.image = null;
    this.staffImageForSrc = null;
    this.staffImageFile = null;
  }

  onClickCancel() {
    this.event.emit('Cancel');
    this.closeModal();
  }

  closeModal() {
    this.bsModalRef.hide();
  }

  private createSafeResourceUrlForImage(imageBase64Url: string): SafeResourceUrl  {
    return this.domSanitize.bypassSecurityTrustResourceUrl('data:image/png;base64, ' + imageBase64Url);
  }
}
