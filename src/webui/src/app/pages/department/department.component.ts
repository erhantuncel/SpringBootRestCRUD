import { DepartmentService } from './../../services/shared/department.service';
import { Component, OnInit, Input } from '@angular/core';
import { PaginationInstance } from 'ngx-pagination';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { AddupdatedepartmentComponent } from './addupdatedepartment/addupdatedepartment.component';

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

  @Input() pageSizeSelect: string;

  public config: PaginationInstance = {
    itemsPerPage: 5,
    currentPage: 1,
    totalItems: 0
  };

  constructor(private departmentService: DepartmentService,
              private modalService: BsModalService) { }

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
}
