import { SharedModule } from "./../shared/shared.module";
import { MaterialModule } from "./../core/material.module";
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { LoginComponent } from "./login/login.component";
import { RegistationComponent } from "./registation/registation.component";

@NgModule({
  declarations: [LoginComponent, RegistationComponent],
  imports: [CommonModule, MaterialModule, SharedModule]
})
export class AuthModule {}
