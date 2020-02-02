import { TranslateService } from '@ngx-translate/core';
import { PaginationInstance } from 'ngx-pagination';
import { Component, OnInit, Input } from '@angular/core';
import { StaffService } from './../../services/shared/staff.service';
import { ActivatedRoute } from '@angular/router';
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

  searchTypes = ['firstName', 'lastName'];
  searchTypeSelectValue: string;
  keywordInputValue: string;

  constructor(private route: ActivatedRoute,
              private staffService: StaffService,
              private toastr: ToastrService,
              private translate: TranslateService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.departmentId = params.id;
    });
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
        this.translate.get('STAFF.toastr.staff.not.found').subscribe(getStaffPageResponse => {
          this.toastr.error(getStaffPageResponse);
        });
      }
    });
  }

  onPageSizeSelected(selectedValue: any) {
    this.config.itemsPerPage = selectedValue;
    this.getPage(1);
  }

  onSubmitSearchForm() {
    console.log('Search Type = ' + this.searchTypeSelectValue);
    console.log('keyWord = ' + this.keywordInputValue);
  }
}
