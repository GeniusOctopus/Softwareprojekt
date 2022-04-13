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
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {NgChartsModule} from "ng2-charts";
import {MatCardModule} from "@angular/material/card";
import {StatisticService} from "../service/statisticService";

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
        MatTabsModule,
        MatTableModule,
        MatSortModule,
        MatPaginatorModule,
        MatFormFieldModule,
        MatInputModule,
        NgChartsModule,
        MatCardModule
    ],
  providers: [
    DumpService,
    ImageService,
    VoteService,
    StatisticService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
