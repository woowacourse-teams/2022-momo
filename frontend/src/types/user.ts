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
  loginType?: LoginType;
  user?: UserProfile;
}

export type LoginType = 'basic' | 'oauth';
export type EditableType = 'name' | 'password';

export interface OAuth {
  oauthLink: string;
}

export interface Token {
  accessToken: string;
  refreshToken: string;
}
