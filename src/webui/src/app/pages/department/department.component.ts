import { DepartmentService } from './../../services/shared/department.service';
import { Component, OnInit, Input } from '@angular/core';
import { PaginationInstance } from 'ngx-pagination';

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css']
})
export class DepartmentComponent implements OnInit {

  data = [];
  pageSize = '5';
  pageSizeOptions = ['5', '10', '15'];

  @Input() pageSizeSelect: string;

  public config: PaginationInstance = {
    itemsPerPage: 5,
    currentPage: 1,
    totalItems: 0
  };

  constructor(private departmentService: DepartmentService) { }

  ngOnInit() {
    this.config.itemsPerPage = Number(this.pageSize);
    this.getPage(1);
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
}
