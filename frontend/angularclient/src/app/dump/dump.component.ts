import { Component, OnInit } from '@angular/core';
import { Observable, throwError } from "rxjs";
import { catchError, retry } from "rxjs";
import { DumpService } from '../../service/dumpService';
import { Dump } from "../../model/dump";

@Component({
  selector: 'app-dump',
  templateUrl: './dump.component.html',
  styleUrls: ['./dump.component.css']
})
export class DumpComponent implements OnInit {

  dumps: Dump[] | undefined;

  constructor(private dumpService: DumpService) { }

  showDumps() {
    this.dumpService.getDumps()
      .subscribe(
        (response) => {
          this.dumps = response;
        }
      )
  }

  ngOnInit(): void {
  }

}
