import { TranslateService } from '@ngx-translate/core';
import { ConfirmationmodalComponent } from './../../shared/confirmationmodal/confirmationmodal.component';
import { DepartmentService } from './../../services/shared/department.service';
import { Component, OnInit, Input } from '@angular/core';
import { PaginationInstance } from 'ngx-pagination';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { AddupdatedepartmentComponent } from './addupdatedepartment/addupdatedepartment.component';
import { ToastrService } from 'ngx-toastr';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css']
})
export class DepartmentComponent implements OnInit {

  data = [];
  pageSize = '5';
  pageSizeOptions = ['5', '10', '15'];

  addUpdateDepartmentModal: BsModalRef;
  deleteDepartmentConfirmationModal: BsModalRef;

  @Input() pageSizeSelect: string;

  public config: PaginationInstance = {
    itemsPerPage: 5,
    currentPage: 1,
    totalItems: 0
  };

  constructor(private departmentService: DepartmentService,
              private modalService: BsModalService,
              private toastr: ToastrService,
              private translate: TranslateService,
              private sanitizer: DomSanitizer) { }

  ngOnInit() {
    this.config.itemsPerPage = Number(this.pageSize);
    this.getPage(1);
  }

  getPage(page: number) {
    this.departmentService.getWithPagination({page: page - 1, size: this.config.itemsPerPage, sort: 'id'}).subscribe(response => {
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

  showAddUpdateDepartmentDialogComponent(id: number, name: string) {
    const config = {
      initialState: {
        departmentId: id,
        departmentNameStatus: name
      },
      backdrop: true,
      ignoreBackdropClick: true,
    };

    this.addUpdateDepartmentModal = this.modalService.show(AddupdatedepartmentComponent, config);
    this.addUpdateDepartmentModal.content.onAction.subscribe((actionType: string) => {
      if (actionType === 'saveOrUpdate') {
        this.getPage(1);
      }
    });
  }

  showDeleteDepartmentConfirmationModalComponent(depId: number, depName: string) {
    let headerTitleTranslated: string;
    let warningMessageTranslated: SafeHtml;
    this.translate.get('DEPARTMENT.DELETECONFIRMATIONMODAL.header.title').subscribe(headerTitleResponse => {
      headerTitleTranslated = headerTitleResponse;
    });
    this.translate.get('DEPARTMENT.DELETECONFIRMATIONMODAL.warningMessage',
                        {departmentId: depId, departmentName: depName}).subscribe(warningResponse => {
      warningMessageTranslated = this.sanitizer.bypassSecurityTrustHtml(warningResponse);
    });
    const config = {
      initialState: {
        departmentId: depId,
        departmentNameStatus: depName,
        headerTitleText: headerTitleTranslated,
        warningMessage: warningMessageTranslated
      },
      backdrop: true,
      ignoreBackdropClick: true,
    };

    this.deleteDepartmentConfirmationModal = this.modalService.show(ConfirmationmodalComponent, config);
    this.deleteDepartmentConfirmationModal.content.onClose.subscribe((isYes: boolean) => {
      if (isYes) {
        console.log('Yes button is clicked.');
        this.departmentService.delete(depId).subscribe(deleteResponse => {
          console.log('Delete response = ' + deleteResponse);
          this.translate.get('DEPARTMENT.DELETECONFIRMATIONMODAL.toastr.deleteDepartment.'
                              + (deleteResponse ? 'success' : 'error') + '.message',
                            {departmentId: depId, departmentName: depName}).subscribe(deleteMessageResponse => {
              if (deleteResponse) {
                this.toastr.success(deleteMessageResponse);
              } else {
                this.toastr.error(deleteMessageResponse);
              }
            });
          this.getPage(1);
        });
      } else {
        console.log('No button is clicked.');
      }
    });
  }
}
