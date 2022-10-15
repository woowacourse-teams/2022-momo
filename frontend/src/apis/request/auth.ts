import { axios, axiosWithAccessToken, axiosWithRefreshToken } from 'apis/axios';
import { API_PATH, BROWSER_PATH } from 'constants/path';
import { OAuth, Token, User } from 'types/user';
import { makeUrl } from 'utils/url';

/**
 * auth.ts 함수의 이름 규칙은 아래 문서를 참고해주세요.
 * @ref https://github.com/woowacourse-teams/2022-momo/wiki/%ED%95%A8%EC%88%98-%EC%9D%B4%EB%A6%84-%EA%B7%9C%EC%B9%99
 */

const requestSignup = (userData: User) => {
  return axios.post(API_PATH.MEMBER.BASE, userData);
};

const requestLogin = (userData: Omit<User, 'name'>) => {
  return axios.post<Token>(API_PATH.AUTH.LOGIN, userData).then(res => res.data);
};

const requestGoogleOauthToken = () => {
  const redirectUrl = `${window.location.origin}${BROWSER_PATH.OAUTH_GOOGLE}`;

  return axios
    .get<OAuth>(makeUrl(API_PATH.AUTH.GOOGLE_LOGIN, { redirectUrl }))
    .then(res => res.data.oauthLink);
};

const requestGoogleLogin = (code: string) => {
  const redirectUrl = `${window.location.origin}${BROWSER_PATH.OAUTH_GOOGLE}`;

  return axios
    .get<Token>(makeUrl(API_PATH.AUTH.GOOGLE_LOGIN, { redirectUrl, code }))
    .then(res => res.data);
};

const requestLogout = () => {
  return axiosWithAccessToken.post(API_PATH.AUTH.LOGOUT);
};

const requestReissueAccessToken = () => {
  return axiosWithRefreshToken
    .post<Pick<Token, 'accessToken'>>(API_PATH.AUTH.REFRESH_ACCESS_TOKEN)
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
