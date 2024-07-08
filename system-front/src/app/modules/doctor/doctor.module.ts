import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DoctorRoutingModule } from './doctor-routing.module';
import { DoctorComponent } from './doctor.component';
import { DoctorVerificationComponent } from './components/doctor-verification/doctor-verification.component';
import { DoctorListComponent } from './components/doctor-list/doctor-list.component';


@NgModule({
  declarations: [
    DoctorComponent,
    DoctorVerificationComponent,
    DoctorListComponent
  ],
  imports: [
    CommonModule,
    DoctorRoutingModule
  ]
})
export class DoctorModule { }
