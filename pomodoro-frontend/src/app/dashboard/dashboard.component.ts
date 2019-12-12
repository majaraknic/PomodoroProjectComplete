import { Pomodoro } from "src/app/dashboard/model/pomodoro";
import { AuthService } from "./../auth/auth.service";
import { DashboardService } from "./dashboard.service";
import { Component, OnInit, ElementRef, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { User } from "../auth/login/model/user";
import { DialogComponent } from "../shared/dialog/dialog.component";
import { MatDialog } from "@angular/material/dialog";
import { Team } from "./model/team";
import { ChangeDetectorRef } from "@angular/core";
@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.css"]
})
export class DashboardComponent implements OnInit {
  form: FormGroup;
  team: Team = new Team();
  noCurrentTeam: boolean = false;
  backendErrorMessage: string = "";
  selectedTeamMember: User;
  pomodoroEndDate: string;
  @ViewChild("counter", { static: false }) counter: ElementRef;

  constructor(
    private dashboardService: DashboardService,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private authService: AuthService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.form = this.fb.group({
      name: ["", Validators.required]
    });
    this.dashboardService.getTeam().subscribe(
      team => {
        this.team = this.sortTeamMembers(team);
        this.dashboardService.currentTeam = team;
        this.noCurrentTeam = false;
      },
      error => {
        if (error.status === 404) {
          this.noCurrentTeam = true;
        }
      }
    );
    this.dashboardService.getUserByEmail().subscribe(user => {
      this.authService.currentUser = user;
    });
  }

  get currentUser() {
    return this.authService.currentUser;
  }

  isFieldInvalid(field: string) {
    return !this.form.get(field).valid && this.form.get(field).touched;
  }

  getErrorMessage(field: string) {
    return this.form.get(field).hasError("required")
      ? "You must enter value"
      : "";
  }

  onSubmit() {
    this.team.users.push(this.authService.currentUser);
    this.team.name = this.form.value.name;
    this.dashboardService.createTeam(this.team).subscribe(
      team => {
        this.backendErrorMessage = "";
        this.noCurrentTeam = false;
        this.team = this.sortTeamMembers(team);

        this.form.reset();
      },
      error => {
        if (error.status === 400) {
          this.backendErrorMessage = "Team name is already in use.";
        }
      }
    );
  }

  setSelectedTeamMember(user: User) {
    this.selectedTeamMember = user;
    this.dashboardService
      .getPomodoroById(user.pomodoro.id)
      .subscribe((pomodoro: Pomodoro) => {
        this.convertDateFormat(pomodoro);
        this.selectedTeamMember.pomodoro = pomodoro;
      });
  }

  onClickAdd() {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: "250px",
      data: { teamId: this.team.id }
    });

    dialogRef.afterClosed().subscribe(team => {
      if (team) {
        this.team = this.sortTeamMembers(team);
        this.selectedTeamMember = undefined;
      }
    });
  }

  onClickExit() {
    this.dashboardService
      .leaveTeam(this.team.id, JSON.parse(localStorage.getItem("user")).email)
      .subscribe(team => {
        this.noCurrentTeam = true;
        this.selectedTeamMember = undefined;
        this.team = new Team();
      });
  }

  startPomodoro() {
    this.selectedTeamMember.pomodoro.startDateTime = new Date();
    this.selectedTeamMember.pomodoro.status = "ACTIVE_TASK";
    this.selectedTeamMember.pomodoro.startDateTime.setHours(
      this.selectedTeamMember.pomodoro.startDateTime.getHours() + 2
    );
    this.dashboardService
      .updatePomodoro(this.selectedTeamMember.pomodoro)
      .subscribe((pomodoro: Pomodoro) => {
        this.convertDateFormat(pomodoro);
      });
  }

  stopPomodoro() {
    this.selectedTeamMember.pomodoro.startDateTime = new Date();
    this.selectedTeamMember.pomodoro.status = "STOPPED";
    this.selectedTeamMember.pomodoro.startDateTime.setHours(
      this.selectedTeamMember.pomodoro.startDateTime.getHours() + 2
    );
    this.dashboardService
      .updatePomodoro(this.selectedTeamMember.pomodoro)
      .subscribe((pomodoro: Pomodoro) => {
        this.selectedTeamMember.pomodoro = pomodoro;
      });
  }

  onTimerCounted($event) {
    if (this.selectedTeamMember.pomodoro.status !== "ACTIVE_PAUSE") {
      this.selectedTeamMember.pomodoro.startDateTime = new Date();
      this.selectedTeamMember.pomodoro.startDateTime.setHours(
        this.selectedTeamMember.pomodoro.startDateTime.getHours() + 2
      );
      this.selectedTeamMember.pomodoro.status = "ACTIVE_PAUSE";
      this.dashboardService
        .updatePomodoro(this.selectedTeamMember.pomodoro)
        .subscribe((pomodoro: Pomodoro) => {
          this.selectedTeamMember.pomodoro.status = "STOPPED";
          this.cd.detectChanges();
          this.convertDateFormat(pomodoro);
        });
    } else {
      this.selectedTeamMember.pomodoro.status = "STOPPED";
    }
  }

  private sortTeamMembers(team: Team): Team {
    let users = [];
    for (let user of team.users) {
      if (user.email === JSON.parse(localStorage.getItem("user")).email) {
        users.push(user);
      }
    }
    users = users.concat(
      team.users.filter(
        user => user.email !== JSON.parse(localStorage.getItem("user")).email
      )
    );
    team.users = users;
    return team;
  }

  private convertDateFormat(pomodoro: Pomodoro) {
    if (pomodoro.startDateTime) {
      this.pomodoroEndDate =
        pomodoro.startDateTime[1] +
        " " +
        pomodoro.startDateTime[2] +
        ", " +
        pomodoro.startDateTime[0] +
        " " +
        pomodoro.startDateTime[3] +
        ":" +
        pomodoro.startDateTime[4] +
        ":" +
        pomodoro.startDateTime[5];
      this.selectedTeamMember.pomodoro = pomodoro;
    }
  }
}
