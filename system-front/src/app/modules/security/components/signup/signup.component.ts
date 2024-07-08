import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "../../../share/services/auth.service";
import { Router } from "@angular/router";
import { CookieManagerService } from "../../../share/services/cookie.service";
import { first } from "rxjs";
import { HttpResponse } from "@angular/common/http";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {

  form: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private cookieManager: CookieManagerService
  ) {
    this.form = new FormGroup({
      fullName: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  signup() {
    if (this.form.valid) {
      this.errorMessage = null;
      this.successMessage = null;

      this.authService.createUser(
        this.form.get('fullName')?.value,
        this.form.get('email')?.value,
        this.form.get('password')?.value
      ).pipe(first())
        .subscribe({
          next: (data: HttpResponse<any>) => {
            this.successMessage = 'Signup successful!';
            setTimeout(() => {
              this.router.navigateByUrl("/security/login");
            }, 2000);
          },
          error: (err) => {
            console.error('Signup error:', err);
            if (err.status === 400) {
              this.errorMessage = 'Username already in use. Please try another one.';
            } else {
              this.errorMessage = 'An unexpected error occurred. Please try again.';
            }
          }
        });
    } else {
      this.errorMessage = 'Please fill out all fields correctly.';
    }
  }
}
