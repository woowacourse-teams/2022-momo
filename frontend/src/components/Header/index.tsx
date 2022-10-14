import { useEffect } from 'react';

import { Link } from 'react-router-dom';

import { requestReissueAccessToken } from 'apis/request/auth';
import { requestUserInfo } from 'apis/request/user';
import { CreateSVG, SignInSVG, SignUpSVG } from 'assets/svg';
import Logo from 'components/svg/Logo';
import { BROWSER_PATH } from 'constants/path';
import useAuth from 'hooks/useAuth';
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
          setUserInfo();
        })
        .catch(() => {
          resetAuth();
        });
    };

    const setUserInfo = () => {
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
    };

    if (accessToken) {
      setUserInfo();
      return;
    }

    reissueAccessToken();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <S.Container>
      <a href={BROWSER_PATH.BASE}>
        <S.Logo>
          <Logo />
        </S.Logo>
      </a>
      <S.Nav>
        {isLogin ? (
          <>
            <Link to={BROWSER_PATH.CREATE}>
              <CreateSVG />
            </Link>
            <User />
          </>
        ) : (
          <>
            <SignUpSVG onClick={showSignupModal} />
            <SignInSVG onClick={showLoginModal} />
          </>
        )}
      </S.Nav>
    </S.Container>
  );
}

export default Header;
