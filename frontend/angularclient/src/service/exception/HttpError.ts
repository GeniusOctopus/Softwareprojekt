import { HttpErrorResponse } from "@angular/common/http";
import { throwError } from "rxjs/internal/observable/throwError";

 export class HttpError {
   public static handleError(error: HttpErrorResponse) {
     if (error.status === 0) {
       console.error('Ein Fehler ist aufgetreten:', error.error);
     } else {
       console.error(
         `Error code ${error.status}, body: `, error.error);
     }
     return throwError(() => new Error('Etwas ist schiefgelaufen; Bitte versuchen Sie es später erneut.'));
   }
}
