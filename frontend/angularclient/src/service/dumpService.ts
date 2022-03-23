import { Injectable } from '@angular/core';

import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError} from 'rxjs/operators';

import { Dump } from '../model/dump';

@Injectable()
export class DumpService {

  dumpUrl = "http://localhost:8080/dump/dumps";

  constructor(private http: HttpClient) {
  }

  getDumps(): Observable<any> {
    return this.http.get(this.dumpUrl)
  }

}
