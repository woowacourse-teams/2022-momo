import { ReactComponent as LogoSVG } from 'assets/logo.svg';
import NavLink from 'components/@shared/NavLink';
import { BROWSER_PATH } from 'constants/path';

import * as S from './index.styled';

function Header() {
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
        <NavLink to={'NOT_THING'}>회원가입</NavLink>
        <NavLink to={'NOT_THING'}>로그인</NavLink>
      </S.Nav>
    </S.Container>
  );
}

export default Header;
