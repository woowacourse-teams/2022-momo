import {
  NewGroupSVG,
  MyGroupSVG,
  FilledHeartSVG,
  SignInSVG,
  SignUpSVG,
} from 'assets/svg';
import NavLink from 'components/NavLink';
import { BROWSER_PATH } from 'constants/path';
import useAuth from 'hooks/useAuth';
import useModal from 'hooks/useModal';

import * as S from './index.styled';

function Footer() {
  const { isLogin } = useAuth();

  const { showLoginModal, showSignupModal } = useModal();

  return (
    <S.Container>
      {isLogin ? (
        <>
          <NavLink to={BROWSER_PATH.MY_GROUP}>
            <S.Button>
              <MyGroupSVG />내 모임
            </S.Button>
          </NavLink>
          <NavLink to={BROWSER_PATH.CREATE}>
            <S.Button>
              <NewGroupSVG />
              모임 만들기
            </S.Button>
          </NavLink>
          <NavLink to={BROWSER_PATH.MY_INFORMATION}>
            <S.Button>
              <FilledHeartSVG />내 정보
            </S.Button>
          </NavLink>
        </>
      ) : (
        <>
          <S.Button onClick={showSignupModal}>
            <SignUpSVG />
            회원가입
          </S.Button>
          <S.Button onClick={showLoginModal}>
            <SignInSVG />
            로그인
          </S.Button>
        </>
      )}
    </S.Container>
  );
}

export default Footer;
