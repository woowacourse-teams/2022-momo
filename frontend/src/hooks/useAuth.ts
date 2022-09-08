import {
  useSetRecoilState,
  useRecoilValue,
  useResetRecoilState,
  useRecoilState,
} from 'recoil';
import { accessTokenState, refreshTokenState, loginState } from 'store/states';
import { UserProfile } from 'types/user';

const useAuth = () => {
  const resetLoginInfo = useResetRecoilState(loginState);
  const [{ isLogin, user }, setLoginInfo] = useRecoilState(loginState);

  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  const [refreshToken, setRefreshToken] = useRecoilState(refreshTokenState);

  const setAuth = (
    userInfo: UserProfile,
    accessToken: string,
    refreshToken: string,
  ) => {
    setAccessToken(accessToken);
    setRefreshToken(refreshToken);

    setLoginInfo({ isLogin: true, loginType: 'basic', user: userInfo });
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
  };
};

export default useAuth;
