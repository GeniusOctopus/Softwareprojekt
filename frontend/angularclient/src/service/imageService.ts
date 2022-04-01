import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class ImageService {

  imageBaseUrl = "http://localhost:8080/image"

  constructor(private http: HttpClient) {

  }

  getImagesForVoting(): Observable<any> {
    return this.http.get(this.imageBaseUrl + "/voting");
  }

  async putIncreaseTimesShownForVoting(imageId: number) {
    return await this.http.put<number>(this.imageBaseUrl + `/increaseTimesShownForVoting/${imageId}`, imageId).toPromise();
  }
}
