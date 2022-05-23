import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddupdatedepartmentComponent } from './addupdatedepartment.component';

describe('AddupdatedepartmentComponent', () => {
  let component: AddupdatedepartmentComponent;
  let fixture: ComponentFixture<AddupdatedepartmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddupdatedepartmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddupdatedepartmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
