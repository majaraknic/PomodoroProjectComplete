import { SharedModule } from "./../shared/shared.module";
import { MaterialModule } from "./../core/material.module";
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DashboardComponent } from "./dashboard.component";

@NgModule({
  declarations: [DashboardComponent],
  imports: [CommonModule, MaterialModule, SharedModule]
})
export class DashboardModule {}
