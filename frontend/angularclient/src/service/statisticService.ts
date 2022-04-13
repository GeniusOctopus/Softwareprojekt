import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {WinnerOnLeftSide} from "../model/winnerOnLeftSide";
import {BasicStatisticData} from "../model/basicStatisticData";
import {DurationStatisticData} from "../model/durationStatisticData";

@Injectable()
export class StatisticService {

  voteBaseUrl = "http://localhost:8080/vote";

  constructor(private http: HttpClient) {
  }

  getCountOfWinnerOnLeftAndRightSide() {
    return this.http.get<WinnerOnLeftSide>(this.voteBaseUrl + "/statistics/countOfWinnerOnLeftAndRightSide");
  }

  getBasicStatisticData() {
    return this.http.get<BasicStatisticData>(this.voteBaseUrl + "/statistics/basicStatisticData");
  }

  getDurationStatistic() {
    return this.http.get<DurationStatisticData>(this.voteBaseUrl + "/statistics/durationStatisticData");
  }
}
