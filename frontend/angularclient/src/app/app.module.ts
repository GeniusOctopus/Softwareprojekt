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
import {VoteService} from "../service/voteService";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatDialogModule} from "@angular/material/dialog";
import { ImageDetailsComponent } from './image-details/image-details.component';
import {MatTabsModule} from "@angular/material/tabs";
import { RankingComponent } from './ranking/ranking.component';

@NgModule({
  declarations: [
    AppComponent,
    DumpComponent,
    VoteComponent,
    HomeComponent,
    StatisticsComponent,
    ImageDetailsComponent,
    RankingComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatDialogModule,
        MatTabsModule
    ],
  providers: [
    DumpService,
    ImageService,
    VoteService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
