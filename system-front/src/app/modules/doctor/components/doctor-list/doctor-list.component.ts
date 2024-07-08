import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../share/services/auth.service";

@Component({
  selector: 'app-doctor-list',
  templateUrl: './doctor-list.component.html',
  styleUrls: ['./doctor-list.component.scss']
})
export class DoctorListComponent implements OnInit{
  constructor(private authService:AuthService) {
  }
  ngOnInit(): void {
    this.authService.getDoctors().subscribe(response=>{
      console.log(response)
    })
  }

}
