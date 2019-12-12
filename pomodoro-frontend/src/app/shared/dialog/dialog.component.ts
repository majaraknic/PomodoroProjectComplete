import { DialogService } from "./dialog.service";
import { Component, OnInit, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Team } from "src/app/dashboard/model/team";

@Component({
  selector: "app-dialog",
  templateUrl: "./dialog.component.html",
  styleUrls: ["./dialog.component.css"]
})
export class DialogComponent implements OnInit {
  form: FormGroup;
  teamId: number;
  backendErrorMessage = "";
  team: Team;

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ["", [Validators.required, Validators.email]]
    });
  }

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogService: DialogService
  ) {
    this.teamId = data.teamId;
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

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit() {
    this.dialogService
      .addNewMember(this.teamId, this.form.value.email)
      .subscribe(
        team => {
          this.backendErrorMessage = "";
          this.team = team;
        },
        error => {
          if (error.status === 404) {
            this.backendErrorMessage = "User doesn't exist.";
          } else if (error.status === 400) {
            this.backendErrorMessage = "User is already member of the team.";
          }
          this.team = undefined;
        }
      );
  }
}
