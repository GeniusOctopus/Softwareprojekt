import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class ImageService {

  imageUrl = "http://localhost:8080/image/voting"

  constructor(private http: HttpClient) {

  }

  getImagesForVoting(): Observable<any> {
    return this.http.get(this.imageUrl);
  }
}
