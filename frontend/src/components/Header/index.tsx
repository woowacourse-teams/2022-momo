import { useEffect } from 'react';

import { useRecoilState } from 'recoil';

import { getUserInfo } from 'apis/request/user';
import NavLink from 'components/@shared/NavLink';
import Logo from 'components/svg/Logo';
import { BROWSER_PATH } from 'constants/path';
import useModal from 'hooks/useModal';
import { accessTokenState, loginState } from 'store/states';

import * as S from './index.styled';
import User from './User';

function Header() {
  const [{ isLogin, loginType }, setLoginInfo] = useRecoilState(loginState);
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);

  const { showSignupModal, showLoginModal } = useModal();

  useEffect(() => {
    if (!accessToken) return;

    getUserInfo()
      .then(userInfo => {
        setLoginInfo({
          isLogin: true,
          loginType,
          user: userInfo,
        });
      })
      .catch(() => {
        // 액세스 토큰 만료 시 자동 로그아웃
        setLoginInfo({ isLogin: false });
        setAccessToken('');
      });
  }, [accessToken, loginType, setAccessToken, setLoginInfo]);

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
