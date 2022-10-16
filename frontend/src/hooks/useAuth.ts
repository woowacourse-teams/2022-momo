import { useResetRecoilState, useRecoilState } from 'recoil';

import { accessTokenState, refreshTokenState, loginState } from 'store/states';
import { LoginType, Token, UserProfile } from 'types/user';

const useAuth = () => {
  const [{ isLogin, user }, setLoginInfo] = useRecoilState(loginState);
  const resetLoginInfo = useResetRecoilState(loginState);

  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  const [refreshToken, setRefreshToken] = useRecoilState(refreshTokenState);

  const setAuth = ({ accessToken, refreshToken }: Token) => {
    setAccessToken(accessToken);
    setRefreshToken(refreshToken);
  };

  const setLogin = (loginType: LoginType, userInfo: UserProfile) => {
    setLoginInfo({
      isLogin: true,
      loginType,
      user: userInfo,
    });
  };

  const resetAuth = () => {
    resetLoginInfo();

    setAccessToken('');
    setRefreshToken('');
  };

  return {
    isLogin,
    user,
    setLoginInfo,
    accessToken,
    setAccessToken,
    refreshToken,
    setAuth,
    resetAuth,
    setLogin,
  };
};

export default useAuth;
