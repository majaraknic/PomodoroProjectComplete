import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.css"]
})
export class HeaderComponent implements OnInit {
  constructor() {}

  ngOnInit() {}

  isUserLoggedIn() {
    if (localStorage.getItem("user") === null) {
      return false;
    } else {
      return true;
    }
  }

  onLogoutClick() {
    localStorage.removeItem("user");
  }
}
