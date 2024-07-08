import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../../share/services/auth.service";

@Component({
  selector: 'app-admin-verification',
  templateUrl: './admin-verification.component.html',
  styleUrls: ['./admin-verification.component.scss']
})
export class AdminVerificationComponent implements OnInit{
  constructor(private authService:AuthService, private router:Router) {
  }

  ngOnInit(): void {
    this.verify();
  }

  private verify() {
    this.authService.verify('ADMIN').subscribe(response=>{
      if (response.data){
        this.router.navigateByUrl('/admin/new-doctor');
      }else{
        alert('wrong authentication!')
        this.router.navigateByUrl('/security/login');
      }
    })
  }
}
