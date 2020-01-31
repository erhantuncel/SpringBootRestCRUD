import { PaginationInstance } from 'ngx-pagination';
import { Component, OnInit, Input } from '@angular/core';
import { StaffService } from './../../services/shared/staff.service';
import { ActivatedRoute } from '@angular/router';

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

  @Input() pageSizeSelect: string;

  public config: PaginationInstance = {
    itemsPerPage: 5,
    currentPage: 1,
    totalItems: 0
  };

  constructor(private route: ActivatedRoute,
              private staffService: StaffService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.departmentId = params.id;
    });
    this.config.itemsPerPage = Number(this.pageSize);
    this.getPage(1);
  }

  getPage(page: number) {
    this.staffService
        .getAllWithDepartmentIdPaginated(this.departmentId, {page: page - 1, size: this.config.itemsPerPage, sort: 'id'})
        .subscribe(response => {
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
