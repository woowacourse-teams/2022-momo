import axios from 'apis/axios';
import { API_PATH, BROWSER_PATH } from 'constants/path';
import { Token, User } from 'types/user';
import { accessTokenProvider, refreshTokenProvider } from 'utils/token';

const requestSignup = (userData: User) => {
  return axios.post(API_PATH.SIGNUP, userData);
};

const requestLogin = (userData: Omit<User, 'name'>) => {
  return axios.post<Token>(API_PATH.LOGIN, userData).then(res => res.data);
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
    .get<Token>(
      `${API_PATH.GOOGLE_LOGIN}?redirectUrl=${redirectUrl}&code=${code}`,
    )
    .then(res => res.data);
};

const requestLogout = () => {
  return axios.post(
    API_PATH.LOGOUT,
    {},
    {
      headers: {
        Authorization: `Bearer ${accessTokenProvider.get()}`,
      },
    },
  );
};

const requestReissueAccessToken = () => {
  return axios
    .post<{ accessToken: string }>(
      API_PATH.REISSUE_ACCESS_TOKEN,
      {},
      {
        headers: {
          Authorization: `Bearer ${refreshTokenProvider.get()}`,
        },
      },
    )
    .then(res => res.data.accessToken);
};

export {
  requestSignup,
  requestLogin,
  requestGoogleOauthToken,
  requestGoogleLogin,
  requestLogout,
  requestReissueAccessToken,
};
