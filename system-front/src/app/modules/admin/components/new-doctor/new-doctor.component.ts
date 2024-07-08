import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthService } from '../../../share/services/auth.service';

@Component({
  selector: 'app-new-doctor',
  templateUrl: './new-doctor.component.html',
  styleUrls: ['./new-doctor.component.scss']
})
export class NewDoctorComponent implements OnInit {
  form = new FormGroup({
    name: new FormControl(),
    address: new FormControl(),
    salary: new FormControl(),
    contact: new FormControl(),
  });

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    // Initialize component
  }

  saveDoctor(): void {
    this.authService.saveDoctor(
      this.form.get('name')?.value,
      this.form.get('address')?.value,
      this.form.get('salary')?.value,
      this.form.get('contact')?.value
    ).subscribe(response => {
      console.log(response);

    }, error => {
      console.error('Error saving doctor:', error);

    });
  }
}
