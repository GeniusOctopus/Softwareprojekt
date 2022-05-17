import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Vote} from "../model/vote";
import {Observable} from "rxjs";
import {Ranking} from "../model/ranking";

@Injectable()
export class VoteService {

  voteBaseUrl = "http://localhost:8080/vote";

  constructor(private http: HttpClient) {
  }

  async postVote(vote: Vote): Promise<Observable<Vote>> {

    let response: any;
    await this.http.post<Vote>(this.voteBaseUrl + "/add", vote).toPromise().then(data => {
      response = data
    });
    return response;
  }

  getRanking() {
    return this.http.get<Ranking[]>(this.voteBaseUrl + "/ranking");
  }
}
