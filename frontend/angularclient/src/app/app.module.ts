import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DumpComponent } from './dump/dump.component';
import {DumpService} from "../service/dumpService";
import { VoteComponent } from './vote/vote.component';
import { HomeComponent } from './home/home.component';
import { StatisticsComponent } from './statistics/statistics.component';
import {ImageService} from "../service/imageService";

@NgModule({
  declarations: [
    AppComponent,
    DumpComponent,
    VoteComponent,
    HomeComponent,
    StatisticsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    DumpService,
    ImageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
