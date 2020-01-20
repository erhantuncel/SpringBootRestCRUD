import { BsModalRef } from 'ngx-bootstrap';
import { Subject } from 'rxjs';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-confirmationmodal',
  templateUrl: './confirmationmodal.component.html',
  styleUrls: ['./confirmationmodal.component.css']
})
export class ConfirmationmodalComponent implements OnInit {

  onClose: Subject<boolean>;
  departmentId: number;
  departementName: string;
  headerTitleText: string;
  warningMessage: string;

  constructor(private bsModalRef: BsModalRef) { }

  ngOnInit() {
    this.onClose = new Subject();
  }

  onClickNo() {
    this.onClose.next(false);
    this.bsModalRef.hide();
  }

  onClickYes(event) {
    this.onClose.next(true);
    this.bsModalRef.hide();
  }



}
