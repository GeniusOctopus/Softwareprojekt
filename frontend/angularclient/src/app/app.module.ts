import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DumpComponent } from './dump/dump.component';
import {DumpService} from "../service/dumpService";

@NgModule({
  declarations: [
    AppComponent,
    DumpComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [DumpService],
  bootstrap: [AppComponent]
})
export class AppModule { }
