import { Injectable } from '@angular/core';
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class CookieManagerService {

  constructor(private cookieService:CookieService) { }

  public setCookie(token:string){
    this.cookieService.set('token',token,90,'/');
  }
  public isTokenExist():boolean{
    return this.cookieService.check('token');
  }

  public getToken():string{
    if (this.isTokenExist()){
      return this.cookieService.get('token');
    }
    return '';
  }
}
