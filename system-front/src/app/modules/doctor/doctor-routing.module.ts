import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DoctorComponent } from './doctor.component';
import {LoginComponent} from "../security/components/login/login.component";
import {SignupComponent} from "../security/components/signup/signup.component";
import {DoctorVerificationComponent} from "./components/doctor-verification/doctor-verification.component";
import {DoctorListComponent} from "./components/doctor-list/doctor-list.component";

const routes: Routes = [{ path: '', component: DoctorComponent, children:[
    {path:'doctor-verification',component:DoctorVerificationComponent},
    {path:'all-doctors',component:DoctorListComponent},
   ]
 },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DoctorRoutingModule { }
