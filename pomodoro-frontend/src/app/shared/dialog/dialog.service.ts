import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { HttpHeaders, HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Team } from "src/app/dashboard/model/team";

@Injectable({
  providedIn: "root"
})
export class DialogService {
  baseUrl = environment.baseUrl;

  httpOptions = {
    headers: new HttpHeaders({
      Token: JSON.parse(localStorage.getItem("user")).token
    })
  };
  constructor(private http: HttpClient) {}

  addNewMember(teamId: number, email: string): Observable<Team> {
    return this.http.post<Team>(
      this.baseUrl + "/team/add-user",
      { teamId: teamId, email: email },
      this.httpOptions
    );
  }
}
