import { useRef } from 'react';

import { useRecoilValue, useSetRecoilState } from 'recoil';

import { requestLogin, requestGoogleOauthToken } from 'apis/request/auth';
import { getUserInfo } from 'apis/request/user';
import { ReactComponent as GoogleSVG } from 'assets/svg/google_login.svg';
import Modal from 'components/@shared/Modal';
import { GUIDE_MESSAGE } from 'constants/message';
import useModal from 'hooks/useModal';
import useSnackbar from 'hooks/useSnackbar';
import {
  accessTokenState,
  loginState,
  modalState,
  refreshTokenState,
} from 'store/states';
import { showErrorMessage } from 'utils/errorController';

import * as S from './index.styled';

function Login() {
  const setAccessToken = useSetRecoilState(accessTokenState);
  const setRefreshToken = useSetRecoilState(refreshTokenState);
  const setLoginInfo = useSetRecoilState(loginState);

  const modalFlag = useRecoilValue(modalState);
  const { setOffModal, showSignupModal } = useModal();

  const { setMessage } = useSnackbar();

  const userIdRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);

  const login = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!userIdRef.current || !passwordRef.current) return;

    const userId = userIdRef.current.value;
    const password = passwordRef.current.value;

    requestLogin({ userId, password })
      .then(({ accessToken, refreshToken }) => {
        setMessage(GUIDE_MESSAGE.AUTH.LOGIN_SUCCESS);

        setAccessToken(accessToken);
        setRefreshToken(refreshToken);

        getUserInfo().then(userInfo => {
          setLoginInfo({ isLogin: true, loginType: 'basic', user: userInfo });
        });

        setOffModal();
      })
      .catch(({ message }) => {
        alert(showErrorMessage(message));
      });
  };

  const googleLogin = () => {
    requestGoogleOauthToken()
      .then(oauthLink => {
        window.location.assign(oauthLink);
      })
      .catch(({ message }) => {
        alert(showErrorMessage(message));
      });
  };

  return (
    <Modal modalState={modalFlag === 'login'}>
      <S.Form onSubmit={login}>
        <S.Title>로그인</S.Title>
        <S.InputContainer>
          <S.Label>
            아이디
            <S.Input type="text" placeholder="brie" ref={userIdRef} required />
          </S.Label>
          <S.Label>
            비밀번호
            <S.Input
              type="password"
              placeholder="********"
              ref={passwordRef}
              required
            />
          </S.Label>
        </S.InputContainer>
        <S.ButtonContainer>
          <S.Button type="submit">로그인</S.Button>
          <S.SignupButton>
            모모가 처음이신가요? |{' '}
            <span onClick={showSignupModal}>회원가입</span>
          </S.SignupButton>
          <S.Divider>
            <span>or</span>
          </S.Divider>
          <S.OAuthButtonWrapper>
            <S.OAuthButton type="button" onClick={googleLogin}>
              <GoogleSVG />
            </S.OAuthButton>
          </S.OAuthButtonWrapper>
        </S.ButtonContainer>
      </S.Form>
    </Modal>
  );
}

export default Login;
