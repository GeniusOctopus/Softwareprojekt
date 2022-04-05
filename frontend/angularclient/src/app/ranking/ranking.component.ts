import {Component, OnInit, ViewChild} from '@angular/core';
import {Ranking} from "../../model/ranking";
import {ImageDetails} from "../../model/imageDetails";
import {VoteService} from "../../service/voteService";
import {ImageService} from "../../service/imageService";
import {MatDialog} from "@angular/material/dialog";
import {ImageDetailsComponent} from "../image-details/image-details.component";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit {
  displayedColumns: string[] = ['rank', 'image', 'wins', 'loses', 'winsPerVote', 'datetime'];
  ranking: Ranking[] = [];
  imageDetails: ImageDetails = new ImageDetails();
  dataSource: MatTableDataSource<Ranking> = new MatTableDataSource<Ranking>();

  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) matSort = new MatSort();

  constructor(private voteService: VoteService,
              private imageService: ImageService,
              private matDialog: MatDialog) {
  }

  async showDetails(rank: Ranking) {
    await this.imageService.getDetails(rank.image?.id)
      .then(
        (response) => {
          this.matDialog.open(ImageDetailsComponent, {
            width: 'auto',
            height: 'auto',
            maxHeight: '900px',
            maxWidth: '1200px',
            data: {
              ranking: rank,
              imageDetails: response
            }
          })
        }
      )
  }

  ngOnInit(): void {
    this.voteService.getRanking()
      .subscribe(
        (response) => {
          this.ranking = response;
          this.dataSource = new MatTableDataSource(this.ranking);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.matSort;
        }
      )
  }
}
