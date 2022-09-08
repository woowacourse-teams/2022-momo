import { useEffect } from 'react';

import useAuth from 'hooks/useAuth';

import { requestReissueAccessToken } from 'apis/request/auth';
import { requestUserInfo } from 'apis/request/user';
import NavLink from 'components/NavLink';
import Logo from 'components/svg/Logo';
import { BROWSER_PATH } from 'constants/path';
import useModal from 'hooks/useModal';
import { getLoginType } from 'utils/user';

import * as S from './index.styled';
import User from './User';

function Header() {
  const {
    isLogin,
    setLoginInfo,
    resetAuth,
    accessToken,
    setAccessToken,
    refreshToken,
  } = useAuth();

  const { showSignupModal, showLoginModal } = useModal();

  useEffect(() => {
    if (!accessToken && !refreshToken) return;

    const reissueAccessToken = () => {
      requestReissueAccessToken()
        .then(accessToken => {
          setAccessToken(accessToken);
        })
        .catch(() => {
          resetAuth();
        });
    };

    requestUserInfo()
      .then(userInfo => {
        setLoginInfo({
          isLogin: true,
          loginType: getLoginType(userInfo.userId),
          user: userInfo,
        });
      })
      .catch(() => {
        reissueAccessToken();
      });
  }, [accessToken, setAccessToken, refreshToken, setLoginInfo]);

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
