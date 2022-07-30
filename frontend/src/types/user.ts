export interface User {
  userId: string;
  password: string;
  name: string;
}

export interface UserProfile extends Omit<User, 'password'> {
  id: number;
}

export type UserInfo = Omit<User, 'password'>;
