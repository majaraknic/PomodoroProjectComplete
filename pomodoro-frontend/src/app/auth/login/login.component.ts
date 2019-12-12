import { AuthService } from "./../auth.service";
import { Component, OnInit } from "@angular/core";
import {
  FormControl,
  Validators,
  FormGroup,
  FormBuilder
} from "@angular/forms";
import { LoginResponse } from "./model/login-response";
import { Router } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"]
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  backendErrorMessage: string = "";

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.form = this.fb.group({
      email: ["", [Validators.required, Validators.email]],
      password: ["", Validators.required]
    });
  }

  getErrorMessage(field: string) {
    return this.form.get(field).hasError("required")
      ? "You must enter value"
      : this.form.get(field).hasError("email")
      ? "Not a valid " + field
      : "";
  }

  isFieldInvalid(field: string) {
    return !this.form.get(field).valid && this.form.get(field).touched;
  }

  onSubmit() {
    this.authService.login(this.form.value).subscribe(
      (loginResponse: LoginResponse) => {
        this.backendErrorMessage = "";
        localStorage.setItem(
          "user",
          JSON.stringify({
            token: loginResponse.token,
            email: this.form.value.email
          })
        );
        this.router.navigateByUrl("/dashboard");
      },
      error => {
        if (error.status === 401) {
          this.backendErrorMessage = "Bad Credentials";
        }
      }
    );
  }
}
