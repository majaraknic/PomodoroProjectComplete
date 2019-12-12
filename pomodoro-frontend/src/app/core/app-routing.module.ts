import { HistoryComponent } from "./../history/history.component";
import { DashboardComponent } from "./../dashboard/dashboard.component";
import { LoginComponent } from "./../auth/login/login.component";
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { RegistationComponent } from "../auth/registation/registation.component";

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "register", component: RegistationComponent },
  { path: "dashboard", component: DashboardComponent },
  { path: "history", component: HistoryComponent },
  { path: "", redirectTo: "/login", pathMatch: "full" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
