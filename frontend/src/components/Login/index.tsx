import { useRef } from 'react';

import { useRecoilState, useSetRecoilState } from 'recoil';

import { requestLogin, requestGoogleOauthToken } from 'apis/request/auth';
import { getUserInfo } from 'apis/request/user';
import { ReactComponent as GoogleSVG } from 'assets/svg/google_login.svg';
import Modal from 'components/Modal';
import { GUIDE_MESSAGE } from 'constants/message';
import useSnackbar from 'hooks/useSnackbar';
import { accessTokenState, loginState, modalState } from 'store/states';
import { showErrorMessage } from 'utils/errorController';

import * as S from './index.styled';

function Login() {
  const [modalFlag, setModalFlag] = useRecoilState(modalState);
  const setModalState = useSetRecoilState(modalState);
  const setAccessToken = useSetRecoilState(accessTokenState);
  const setLoginInfo = useSetRecoilState(loginState);
  const userIdRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);

  const { setMessage } = useSnackbar();

  const setOffModal = () => {
    setModalFlag('off');
  };

  const login = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!userIdRef.current || !passwordRef.current) return;

    const userId = userIdRef.current.value;
    const password = passwordRef.current.value;

    requestLogin({ userId, password })
      .then(accessToken => {
        setMessage(GUIDE_MESSAGE.AUTH.LOGIN_SUCCESS);

        setAccessToken(accessToken);

        getUserInfo().then(userInfo => {
          setLoginInfo({ isLogin: true, user: userInfo });
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

  const showSignupModal = () => {
    setModalState('signup');
  };

  return (
    <Modal modalState={modalFlag === 'login'} setOffModal={setOffModal}>
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
