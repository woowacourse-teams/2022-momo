import axios from 'apis/axios';
import { API_PATH, BROWSER_PATH } from 'constants/path';
import { User } from 'types/user';

const requestSignup = (userData: User) => {
  return axios.post(API_PATH.SIGNUP, userData);
};

const requestLogin = (userData: Omit<User, 'name'>) => {
  return axios
    .post<{ accessToken: string }>(API_PATH.LOGIN, userData)
    .then(res => res.data.accessToken);
};

const requestGoogleOauthToken = () => {
  const redirectPath = BROWSER_PATH.OAUTH_GOOGLE.toString().substring(1);
  const redirectUrl = `${window.location.origin}/${redirectPath}`;

  return axios
    .get<{ oauthLink: string }>(
      `${API_PATH.GOOGLE_LOGIN}?redirectUrl=${redirectUrl}`,
    )
    .then(res => res.data.oauthLink);
};

const requestGoogleLogin = (code: string) => {
  const redirectUrl = `${window.location.origin}${BROWSER_PATH.OAUTH_GOOGLE}`;

  return axios
    .get<{ accessToken: string; refreshToken: string }>(
      `${API_PATH.GOOGLE_LOGIN}?redirectUrl=${redirectUrl}&code=${code}`,
    )
    .then(res => res.data.accessToken);
};

const requestLogout = () => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios.post(
    API_PATH.LOGOUT,
    {},
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );
};

export {
  requestSignup,
  requestLogin,
  requestGoogleOauthToken,
  requestGoogleLogin,
  requestLogout,
};
