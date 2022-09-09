import { useResetRecoilState, useRecoilState } from 'recoil';

import { accessTokenState, refreshTokenState, loginState } from 'store/states';
import { LoginType, UserProfile } from 'types/user';

const useAuth = () => {
  const resetLoginInfo = useResetRecoilState(loginState);
  const [{ isLogin, user }, setLoginInfo] = useRecoilState(loginState);

  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  const [refreshToken, setRefreshToken] = useRecoilState(refreshTokenState);

  const setAuth = (accessToken: string, refreshToken: string) => {
    setAccessToken(accessToken);
    setRefreshToken(refreshToken);
  };

  const setLogin = (loginType: LoginType, userInfo: UserProfile) => {
    switch (loginType) {
      case 'basic':
        setLoginInfo({ isLogin: true, loginType: 'basic', user: userInfo });
        break;
      case 'oauth':
        setLoginInfo({ isLogin: true, loginType: 'oauth', user: userInfo });
        break;
    }
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
    setRefreshToken,
    setAuth,
    resetAuth,
    setLogin,
  };
};

export default useAuth;
