import { User } from "src/app/auth/login/model/user";

export class Team {
  id: number;
  name: string;
  users: User[] = [];
}
