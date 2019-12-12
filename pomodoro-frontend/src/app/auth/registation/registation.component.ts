import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  Validators,
  AbstractControl
} from "@angular/forms";
import { AuthService } from "../auth.service";
import { User } from "../login/model/user";
import { Router } from "@angular/router";

@Component({
  selector: "app-registation",
  templateUrl: "./registation.component.html",
  styleUrls: ["./registation.component.css"]
})
export class RegistationComponent implements OnInit {
  form: FormGroup;
  backendErrorMessage: string = "";

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.form = this.fb.group(
      {
        email: ["", [Validators.required, Validators.email]],
        password: ["", Validators.required],
        passwordConfirmation: ["", [Validators.required]],
        fullName: ["", [Validators.required]]
      },
      { validator: this.passwordMatchValidator }
    );
  }

  passwordMatchValidator(control: AbstractControl) {
    const password: string = control.get("password").value;
    const confirmPassword: string = control.get("passwordConfirmation").value;
    if (password !== confirmPassword) {
      control.get("passwordConfirmation").setErrors({ NoPassswordMatch: true });
    }
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
    this.authService.register(this.form.value).subscribe(
      (registerResponse: User) => {
        this.backendErrorMessage = "";
        this.router.navigateByUrl("/login");
      },
      error => {
        if (error.status === 400) {
          this.backendErrorMessage = error.error.error;
        }
      }
    );
  }
}
