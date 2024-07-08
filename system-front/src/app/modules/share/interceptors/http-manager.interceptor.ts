import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {catchError, finalize, Observable, throwError} from 'rxjs';
import {CookieManagerService} from "../services/cookie.service";

@Injectable()
export class HttpManagerInterceptor implements HttpInterceptor {

  constructor(private cookieService:CookieManagerService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    console.log('Token: '+this.cookieService.getToken())
    request=request.clone({
      setHeaders:{Authorization:this.cookieService.getToken()}
    });

    return next.handle(request).pipe(
      catchError(error=>{
        if (error.status==403){

        }
        return throwError(error);
      }),
      finalize(()=>{

      })
    );

  }
}
