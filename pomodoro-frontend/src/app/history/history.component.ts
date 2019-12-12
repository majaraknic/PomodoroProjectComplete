import { HistoryService } from "./history.service";
import { AuthService } from "./../auth/auth.service";
import { Component, OnInit } from "@angular/core";
import { PomodoroHistory } from "./model/pomodoro-history";
import { MatTableDataSource } from "@angular/material/table";
import { DashboardService } from "../dashboard/dashboard.service";

@Component({
  selector: "app-history",
  templateUrl: "./history.component.html",
  styleUrls: ["./history.component.css"]
})
export class HistoryComponent implements OnInit {
  pomodoroHistoryList: PomodoroHistory[];
  displayedColumns: string[] = ["email", "time", "activity"];
  noHistoryMessage = "";
  dataSource = new MatTableDataSource();
  constructor(
    private authService: AuthService,
    private dashboardService: DashboardService,
    private historyService: HistoryService
  ) {}

  ngOnInit() {
    this.historyService
      .getTeamHistory(
        this.dashboardService.currentTeam.id,
        this.authService.currentUser.id
      )
      .subscribe(
        (historyList: PomodoroHistory[]) => {
          this.pomodoroHistoryList = historyList;
          for (let pomodoroHistory of this.pomodoroHistoryList) {
            const tableItem = {
              email: pomodoroHistory.user.email,
              time: pomodoroHistory.activityTime,
              activity: pomodoroHistory.activity
            };
            this.dataSource.data.push(tableItem);
            this.noHistoryMessage = "";
          }
          this.dataSource = new MatTableDataSource(this.dataSource.data);
        },
        error => {
          if (error.status === 404) {
            this.dataSource.data = [];
            this.noHistoryMessage = "There is no history for this team.";
          }
        }
      );
  }
}
