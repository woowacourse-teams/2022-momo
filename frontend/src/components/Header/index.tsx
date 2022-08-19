import { useEffect } from 'react';

import { useRecoilState, useResetRecoilState } from 'recoil';

import { requestReissueAccessToken } from 'apis/request/auth';
import { getUserInfo } from 'apis/request/user';
import NavLink from 'components/@shared/NavLink';
import Logo from 'components/svg/Logo';
import { BROWSER_PATH } from 'constants/path';
import useModal from 'hooks/useModal';
import { accessTokenState, loginState, refreshTokenState } from 'store/states';
import { getLoginType } from 'utils/user';

import * as S from './index.styled';
import User from './User';

function Header() {
  const [{ isLogin }, setLoginInfo] = useRecoilState(loginState);
  const resetLoginInfo = useResetRecoilState(loginState);
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  const [refreshToken, setRefreshToken] = useRecoilState(refreshTokenState);

  const { showSignupModal, showLoginModal } = useModal();

  useEffect(() => {
    if (!accessToken && !refreshToken) return;

    const reissueAccessToken = () => {
      requestReissueAccessToken()
        .then(accessToken => {
          // 리프레시 토큰이 유효한 경우
          setAccessToken(accessToken);
        })
        .catch(() => {
          // 리프레시 토큰이 유효하지 않거나 만료인 경우
          resetLoginInfo();

          setAccessToken('');
          setRefreshToken('');
        });
    };

    getUserInfo()
      .then(userInfo => {
        // 액세스 토큰이 유효한 경우
        setLoginInfo({
          isLogin: true,
          loginType: getLoginType(userInfo.userId),
          user: userInfo,
        });
      })
      .catch(() => {
        // 액세스 토큰이 유효하지 않은 경우
        reissueAccessToken();
      });
  }, [
    accessToken,
    setAccessToken,
    refreshToken,
    setRefreshToken,
    setLoginInfo,
    resetLoginInfo,
  ]);

  return (
    <S.Container>
      <NavLink to={BROWSER_PATH.BASE}>
        <S.Logo>
          <Logo />
        </S.Logo>
      </NavLink>
      <S.Nav>
        {isLogin ? (
          <>
            <NavLink to={BROWSER_PATH.CREATE}>모임 생성</NavLink>
            <User />
          </>
        ) : (
          <>
            <div onClick={showSignupModal}>회원가입</div>
            <div onClick={showLoginModal}>로그인</div>
          </>
        )}
      </S.Nav>
    </S.Container>
  );
}

export default Header;
