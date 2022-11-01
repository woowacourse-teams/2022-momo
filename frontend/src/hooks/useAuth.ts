import { useEffect } from 'react';

import { useQueryClient } from 'react-query';
import { useResetRecoilState, useRecoilState, SetterOrUpdater } from 'recoil';

import { QUERY_KEY } from 'constants/key';
import { accessTokenState, refreshTokenState, loginState } from 'store/states';
import { LoginState, LoginType, Token, UserProfile } from 'types/user';

interface UseAuthReturnType {
  isLogin: boolean;
  user?: UserProfile;
  setLoginInfo: SetterOrUpdater<LoginState>;
  accessToken: string;
  setAccessToken: SetterOrUpdater<string>;
  refreshToken: string;
  setAuth: ({ accessToken, refreshToken }: Token) => void;
  resetAuth: () => void;
  setLogin: (loginType: LoginType, userInfo: UserProfile) => void;
}

const useAuth = (): UseAuthReturnType => {
  const [{ isLogin, user }, setLoginInfo] = useRecoilState(loginState);
  const resetLoginInfo = useResetRecoilState(loginState);

  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  const [refreshToken, setRefreshToken] = useRecoilState(refreshTokenState);

  const queryClient = useQueryClient();

  useEffect(() => {
    queryClient.invalidateQueries(QUERY_KEY.GROUP_DETAILS);
    queryClient.invalidateQueries(QUERY_KEY.GROUP_SUMMARIES);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isLogin]);

  const setAuth = ({ accessToken, refreshToken }: Token): void => {
    setAccessToken(accessToken);
    setRefreshToken(refreshToken);
  };

  const setLogin = (loginType: LoginType, userInfo: UserProfile): void => {
    setLoginInfo({
      isLogin: true,
      loginType,
      user: userInfo,
    });
  };

  const resetAuth = (): void => {
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
