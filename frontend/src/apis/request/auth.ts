import axios from 'apis/axios';
import { API_PATH } from 'constants/path';
import { User } from 'types/user';

const requestSignup = (userData: User) => {
  return axios.post(API_PATH.SIGNUP, userData);
};

const requestLogin = (userData: Omit<User, 'name'>) => {
  return axios
    .post<{ accessToken: string }>(API_PATH.LOGIN, userData)
    .then(res => res.data.accessToken);
};

export { requestSignup, requestLogin };
