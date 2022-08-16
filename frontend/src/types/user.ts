export interface User {
  userId: string;
  password: string;
  name: string;
}

export interface UserProfile extends Omit<User, 'password'> {
  id: number;
}

export interface LoginState {
  isLogin: boolean;
  loginType?: 'basic' | 'oauth';
  user?: UserProfile;
}
