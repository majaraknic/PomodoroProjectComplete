import { LoginResponse } from "./login/model/login-response";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Credentials } from "./login/model/credentials";
import { environment } from "./../../environments/environment";
import { User } from "./login/model/user";
@Injectable({
  providedIn: "root"
})
export class AuthService {
  baseUrl = environment.baseUrl;
  currentUser: User;
  constructor(private http: HttpClient) {}

  login(credentials: Credentials): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      this.baseUrl + "/user/login",
      credentials
    );
  }

  register(user: User): Observable<User> {
    return this.http.post<User>(this.baseUrl + "/user/register", user);
  }
}
