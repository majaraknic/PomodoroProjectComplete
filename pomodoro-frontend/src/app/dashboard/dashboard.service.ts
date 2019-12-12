import { AuthService } from "./../auth/auth.service";
import { Pomodoro } from "./model/pomodoro";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { User } from "../auth/login/model/user";
import { Team } from "./model/team";

@Injectable({
  providedIn: "root"
})
export class DashboardService {
  baseUrl = environment.baseUrl;
  currentTeam: Team;
  httpOptions = {
    headers: new HttpHeaders({
      Token: JSON.parse(localStorage.getItem("user")).token
    })
  };

  constructor(private http: HttpClient, private authService: AuthService) {}

  getTeam(): Observable<Team> {
    return this.http.get<Team>(
      this.baseUrl +
        "/team/email?email=" +
        JSON.parse(localStorage.getItem("user")).email,
      this.httpOptions
    );
  }

  createTeam(team: Team): Observable<Team> {
    return this.http.post<Team>(this.baseUrl + "/team", team, this.httpOptions);
  }

  getUserByEmail(): Observable<User> {
    return this.http.get<User>(
      this.baseUrl +
        "/user/email?email=" +
        JSON.parse(localStorage.getItem("user")).email,
      this.httpOptions
    );
  }

  leaveTeam(teamId: number, email: string): Observable<Team> {
    return this.http.post<Team>(
      this.baseUrl + "/team/delete-user",
      { teamId: teamId, email: email },
      this.httpOptions
    );
  }

  updatePomodoro(pomodoro: Pomodoro) {
    return this.http.put<Pomodoro>(
      this.baseUrl + "/pomodoro/" + this.authService.currentUser.id,
      pomodoro,
      this.httpOptions
    );
  }

  getPomodoroById(pomodoroId: number): Observable<Pomodoro> {
    return this.http.get<Pomodoro>(
      this.baseUrl + "/pomodoro/" + pomodoroId,
      this.httpOptions
    );
  }
}
