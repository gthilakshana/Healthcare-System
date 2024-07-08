import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import {LoginComponent} from "../security/components/login/login.component";
import {SignupComponent} from "../security/components/signup/signup.component";
import {AdminVerificationComponent} from "./components/admin-verification/admin-verification.component";
import {NewDoctorComponent} from "./components/new-doctor/new-doctor.component";

const routes: Routes = [{ path: '', component: AdminComponent,children:[
    {path:'admin-verification',component:AdminVerificationComponent},
    {path:'new-doctor',component:NewDoctorComponent},
  ]
 },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
