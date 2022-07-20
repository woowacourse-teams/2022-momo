import { useSetRecoilState } from 'recoil';

import { ReactComponent as LogoSVG } from 'assets/logo.svg';
import NavLink from 'components/@shared/NavLink';
import { BROWSER_PATH } from 'constants/path';
import { modalState } from 'store/states';
import { ModalStateType } from 'types/condition';

import * as S from './index.styled';

function Header() {
  const setModalState = useSetRecoilState(modalState);

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
        <NavLink to={BROWSER_PATH.CREATE}>모임 생성</NavLink>
        <NavLink to={'NOT_THING'}>내 모임</NavLink>
        <div onClick={changeModalState('signup')}>회원가입</div>
        <div onClick={changeModalState('login')}>로그인</div>
      </S.Nav>
    </S.Container>
  );
}

export default Header;
