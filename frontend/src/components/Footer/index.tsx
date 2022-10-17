import { Link } from 'react-router-dom';

import {
  NewGroupSVG,
  MyGroupSVG,
  FilledHeartSVG,
  SignInSVG,
  SignUpSVG,
} from 'assets/svg';
import { BROWSER_PATH } from 'constants/path';
import useAuth from 'hooks/useAuth';
import useModal from 'hooks/useModal';

import * as S from './index.styled';

const svgSize = 26;

function Footer() {
  const { isLogin } = useAuth();

  const { showLoginModal, showSignupModal } = useModal();

  return (
    <S.Container>
      {isLogin ? (
        <>
          <Link to={BROWSER_PATH.MY_GROUP}>
            <S.Button>
              <MyGroupSVG />내 모임
            </S.Button>
          </Link>
          <Link to={BROWSER_PATH.CREATE}>
            <S.Button>
              <NewGroupSVG />
              모임 만들기
            </S.Button>
          </Link>
          <Link to={BROWSER_PATH.MY_INFORMATION}>
            <S.Button>
              <FilledHeartSVG width={svgSize} height={svgSize} />내 정보
            </S.Button>
          </Link>
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
