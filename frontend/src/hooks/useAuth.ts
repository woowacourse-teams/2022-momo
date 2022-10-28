import { useEffect } from 'react';

import { useQueryClient } from 'react-query';
import { useResetRecoilState, useRecoilState } from 'recoil';

import { QUERY_KEY } from 'constants/key';
import { accessTokenState, refreshTokenState, loginState } from 'store/states';
import { LoginType, Token, UserProfile } from 'types/user';

const useAuth = () => {
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
