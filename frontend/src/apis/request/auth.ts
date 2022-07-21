import axios from 'apis/axios';
import { API_PATH } from 'constants/path';
import { User } from 'types/user';

const requestSignup = (userData: User): Promise<void> => {
  return axios.post(API_PATH.SIGNUP, userData);
};

const requestLogin = (userData: Omit<User, 'name'>): Promise<string> => {
  return axios.post(API_PATH.LOGIN, userData).then(res => res.data.accessToken);
};

export { requestSignup, requestLogin };
