import { useEffect } from 'react';

import { useRecoilState, useSetRecoilState } from 'recoil';

import { ReactComponent as LogoSVG } from 'assets/logo.svg';
import NavLink from 'components/@shared/NavLink';
import { GUIDE_MESSAGE } from 'constants/message';
import { BROWSER_PATH } from 'constants/path';
import { accessTokenState, loginState, modalState } from 'store/states';
import { ModalStateType } from 'types/condition';

import * as S from './index.styled';

function Header() {
  const setModalState = useSetRecoilState(modalState);
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  const [isLogin, setIsLogin] = useRecoilState(loginState);

  useEffect(() => {
    if (accessToken) {
      setIsLogin(true);
    }
  }, [accessToken, setIsLogin]);

  const changeModalState = (modalState: ModalStateType) => () => {
    setModalState(modalState);
  };

  const logout = () => {
    if (!window.confirm(GUIDE_MESSAGE.AUTH.CONFIRM_LOGOUT)) return;

    setIsLogin(false);
    setAccessToken('');
  };

  return (
    <S.Container>
      <NavLink to={BROWSER_PATH.BASE}>
        <S.Logo>
          <LogoSVG />
        </S.Logo>
      </NavLink>
      <S.Nav>
        {isLogin ? (
          <>
            <NavLink to={BROWSER_PATH.CREATE}>모임 생성</NavLink>
            <NavLink to={'NOT_THING'}>내 모임</NavLink>
            <NavLink to={BROWSER_PATH.INFO}>내 정보</NavLink>
            <div onClick={logout}>로그아웃</div>
          </>
        ) : (
          <>
            <div onClick={changeModalState('signup')}>회원가입</div>
            <div onClick={changeModalState('login')}>로그인</div>
          </>
        )}
      </S.Nav>
    </S.Container>
  );
}

export default Header;
