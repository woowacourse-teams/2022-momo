import { useRecoilValue } from 'recoil';

import NavLink from 'components/@shared/NavLink';
import Logo from 'components/svg/Logo';
import { BROWSER_PATH } from 'constants/path';
import useModal from 'hooks/useModal';
import { loginState } from 'store/states';

import * as S from './index.styled';
import User from './User';

function Header() {
  const { isLogin } = useRecoilValue(loginState);

  const { showSignupModal, showLoginModal } = useModal();

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
