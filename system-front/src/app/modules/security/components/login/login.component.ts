import { Component } from '@angular/core';
import { AuthService } from "../../../share/services/auth.service";
import { Router } from "@angular/router";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { first } from "rxjs";
import { HttpResponse } from "@angular/common/http";
import { CookieManagerService } from "../../../share/services/cookie.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  form: FormGroup;
  errorMessage: string | null = null;
  isLoading: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private cookieManager: CookieManagerService
  ) {
    this.form = new FormGroup({
      email: new FormControl('', [Validators.email, Validators.required]),
      password: new FormControl('', Validators.required)
    });
  }

  login() {
    if (this.form.valid) {
      this.isLoading = true;
      this.errorMessage = null;

      this.authService.login(
        this.form.get('email')?.value,
        this.form.get('password')?.value
      ).pipe(first())
        .subscribe({
          next: (data: HttpResponse<any>) => {
            this.cookieManager.setCookie(data.headers.get('Authorization')!);
            this.isLoading = false;
            this.router.navigateByUrl("/");
          },
          error: (err) => {
            console.error('Login error:', err);
            this.isLoading = false;
            if (err.status === 401) {
              this.errorMessage = 'Invalid credentials. Please try again.';
            } else if (err.status === 0) {
              this.errorMessage = 'Cannot connect to the server. Please try again later.';
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
