import { PomodoroHistory } from "./model/pomodoro-history";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { HttpHeaders, HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class HistoryService {
  baseUrl = environment.baseUrl;

  httpOptions = {
    headers: new HttpHeaders({
      Token: JSON.parse(localStorage.getItem("user")).token
    })
  };

  constructor(private http: HttpClient) {}

  getTeamHistory(
    teamId: number,
    userId: number
  ): Observable<PomodoroHistory[]> {
    return this.http.get<PomodoroHistory[]>(
      this.baseUrl + "/pomodoro-history/" + teamId + "/" + userId,
      this.httpOptions
    );
  }
}
