import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { HistoryComponent } from "./history.component";
import { MaterialModule } from "../core/material.module";
import { SharedModule } from "../shared/shared.module";

@NgModule({
  declarations: [HistoryComponent],
  imports: [CommonModule, MaterialModule, SharedModule]
})
export class HistoryModule {}
