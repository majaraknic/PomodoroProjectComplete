import { MaterialModule } from "./../core/material.module";
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { HeaderComponent } from "./header/header.component";
import { RouterModule } from "@angular/router";
import { FlexLayoutModule } from "@angular/flex-layout";
import { DialogComponent } from "./dialog/dialog.component";
import { MatDividerModule } from "@angular/material/divider";
import { CountdownModule } from "ng2-date-countdown";
@NgModule({
  declarations: [HeaderComponent, DialogComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    MaterialModule,
    FlexLayoutModule,
    MatDividerModule,
    CountdownModule
  ],
  exports: [
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    HeaderComponent,
    FlexLayoutModule,
    MatDividerModule,
    CountdownModule
  ],
  entryComponents: [DialogComponent]
})
export class SharedModule {}
