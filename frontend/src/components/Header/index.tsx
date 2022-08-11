import { useRecoilValue, useSetRecoilState } from 'recoil';

import { ReactComponent as LogoSVG } from 'assets/svg/logo.svg';
import NavLink from 'components/@shared/NavLink';
import { BROWSER_PATH } from 'constants/path';
import { loginState, modalState } from 'store/states';
import { ModalStateType } from 'types/condition';

import * as S from './index.styled';
import User from './User';

function Header() {
  const setModalState = useSetRecoilState(modalState);
  const { isLogin } = useRecoilValue(loginState);

  const changeModalState = (modalState: ModalStateType) => () => {
    setModalState(modalState);
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
            <User />
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
