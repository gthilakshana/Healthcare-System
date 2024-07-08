import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SecurityRoutingModule } from './security-routing.module';
import { SecurityComponent } from './security.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    SecurityComponent,
    LoginComponent,
    SignupComponent
  ],
    imports: [
        CommonModule,
        SecurityRoutingModule,
        ReactiveFormsModule
    ]
})
export class SecurityModule { }
