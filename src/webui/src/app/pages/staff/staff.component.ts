import { ConfirmationmodalComponent } from './../../shared/confirmationmodal/confirmationmodal.component';
import { SafeHtml, DomSanitizer } from '@angular/platform-browser';
import { AddupdatestaffmodalComponent } from './addupdatestaffmodal/addupdatestaffmodal.component';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { DepartmentService } from './../../services/shared/department.service';
import { TranslateService } from '@ngx-translate/core';
import { PaginationInstance } from 'ngx-pagination';
import { Component, OnInit, Input } from '@angular/core';
import { StaffService } from './../../services/shared/staff.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-staff',
  templateUrl: './staff.component.html',
  styleUrls: ['./staff.component.css']
})
export class StaffComponent implements OnInit {

  departmentId: number;
  data = [];
  pageSize = '5';
  pageSizeOptions = ['5', '10', '15'];
  keywordMinLength = 3;

  @Input() pageSizeSelect: string;

  public config: PaginationInstance = {
    itemsPerPage: 5,
    currentPage: 1,
    totalItems: 0
  };

  queryParams = {
    size: this.config.itemsPerPage,
    sort: 'id',
  };

  departments = [];
  searchTypes = ['firstName', 'lastName'];
  searchTypeSelectValue: string;
  keywordInputValue: string;

  addUpdateStaffModal: BsModalRef;
  deleteStaffConfirmationModal: BsModalRef;

  constructor(private staffService: StaffService,
              private departmentService: DepartmentService,
              private toastr: ToastrService,
              private translate: TranslateService,
              private modalService: BsModalService,
              private sanitizer: DomSanitizer) { }

  ngOnInit() {
    this.populateDepartments();
    this.departmentId = 1;
    this.searchTypeSelectValue = this.searchTypes[0];
    this.config.itemsPerPage = Number(this.pageSize);
    this.getPage(1);
  }

  getPage(page: number) {
    this.queryParams[String('page')] = page - 1;
    if (this.keywordInputValue != null && this.keywordInputValue !== '') {
      for (const searchType of this.searchTypes) {
        if (this.queryParams.hasOwnProperty(searchType)) {
          delete this.queryParams[searchType];
        }
      }
      this.queryParams[this.searchTypeSelectValue] = this.keywordInputValue;
    } else {
      delete this.queryParams[this.searchTypeSelectValue];
    }
    this.staffService.getAllWithDepartmentIdPaginated(this.departmentId, this.queryParams).subscribe(response => {
      if (response.status === 200) {
        console.log(response);
        this.data = response.body.content;
        this.config.currentPage = page;
        this.config.totalItems = response.body.totalElements;
      } else {
        this.data = [];
        this.translate.get('STAFF.toastr.staff.not.found').subscribe(getStaffPageResponse => {
          this.toastr.error(getStaffPageResponse);
        });
      }
    });
  }

  populateDepartments() {
    this.departmentService.getAll().subscribe(allDepartments => {
      this.departments = allDepartments.body;
    });
  }

  onPageSizeSelected(selectedValue: any) {
    this.config.itemsPerPage = selectedValue;
    this.getPage(1);
  }

  onDepartmentSelected() {
    for (const searchType of this.searchTypes) {
      if (this.queryParams.hasOwnProperty(searchType)) {
        delete this.queryParams[searchType];
      }
    }
    this.keywordInputValue = '';
    this.data = [];
    this.getPage(1);
  }

  onSubmitSearchForm() {
    console.log('Search Type = ' + this.searchTypeSelectValue);
    console.log('keyWord = ' + this.keywordInputValue);
  }

  showAddUpdateStaffModalComponent(staffIdFromTable: number) {
    const config =  {
      initialState: {
        departmentId: this.departmentId,
        staffId: staffIdFromTable
      },
      class: 'modal-lg',
      backdrop: true,
      ignoreBackdropClick: true,
    };

    this.addUpdateStaffModal = this.modalService.show(AddupdatestaffmodalComponent, config);
    this.addUpdateStaffModal.content.event.subscribe(result => {
      if (result === 'Updated') {
        console.log('Staff is updated.');
        this.getPage(1);
      } else if (result === 'Added') {
        console.log('Staff is added.');
        this.getPage(1);
      } else if (result === 'Cancel') {
        console.log('Cancel button clicked.');
      }
    });
  }

  showDeleteStaffConfirmationModalComponent(staffIdFromTable: number, staffFirstName: string, staffLastName: string) {
    let headerTitleTranslated: string;
    let warningMessageTranslated: SafeHtml;

    this.translate.get('STAFF.DELETECONFIRMATIONMODAL.header.title').subscribe(headerTitleResponse => {
      headerTitleTranslated = headerTitleResponse;
    });
    this.translate.get('STAFF.DELETECONFIRMATIONMODAL.warningMessage',
                       {staffId: staffIdFromTable, firstName: staffFirstName, lastName: staffLastName})
                       .subscribe(warningMessageResponse => {
      warningMessageTranslated = this.sanitizer.bypassSecurityTrustHtml(warningMessageResponse);
    });

    const config = {
      initialState: {
        departmentId: this.departmentId,
        staffId: staffIdFromTable,
        headerTitleText: headerTitleTranslated,
        warningMessage: warningMessageTranslated
      },
      backdrop: true,
      ignoreBackdropClick: true,
    };

    this.deleteStaffConfirmationModal = this.modalService.show(ConfirmationmodalComponent, config);
    this.deleteStaffConfirmationModal.content.onClose.subscribe((isYes: boolean) => {
      if (isYes) {
        this.staffService.delete(this.departmentId, staffIdFromTable).subscribe(deleteResponse => {
          console.log(deleteResponse);
          if (deleteResponse.status === 200) {
            this.translate.get('STAFF.DELETECONFIRMATIONMODAL.toastr.deleteDepartment.success.message',
                               {staffId: staffIdFromTable, firstName: staffFirstName, lastName: staffLastName})
                               .subscribe(deleteSuccessResponse => {
              this.toastr.success(deleteSuccessResponse);
            });
          } else {
            this.translate.get('STAFF.DELETECONFIRMATIONMODAL.toastr.deleteDepartment.error.message',
                               {staffId: staffIdFromTable, firstName: staffFirstName, lastName: staffLastName})
                               .subscribe(deleteErrorResponse => {
              this.toastr.error(deleteErrorResponse);
            });
          }
          this.getPage(1);
        });
      } else {
        console.log('No button is clicked.');
      }
    });
  }
}
