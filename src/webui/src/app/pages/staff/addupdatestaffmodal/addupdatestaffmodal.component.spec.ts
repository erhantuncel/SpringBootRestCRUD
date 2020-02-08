import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddupdatestaffmodalComponent } from './addupdatestaffmodal.component';

describe('AddupdatestaffmodalComponent', () => {
  let component: AddupdatestaffmodalComponent;
  let fixture: ComponentFixture<AddupdatestaffmodalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddupdatestaffmodalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddupdatestaffmodalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
