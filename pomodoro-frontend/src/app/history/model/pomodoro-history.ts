import { Team } from "src/app/dashboard/model/team";
import { User } from "src/app/auth/login/model/user";

export class PomodoroHistory {
  id: number;
  team: Team;
  user: User;
  activityTime: Date;
  activity: string;
}
