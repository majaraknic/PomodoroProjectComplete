import { Pomodoro } from "src/app/dashboard/model/pomodoro";

export class User {
  id: number;
  email: string;
  password: string;
  fullName: string;
  pomodoro: Pomodoro;
}
