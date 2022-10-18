import { useRef } from 'react';

import { useQueryClient } from 'react-query';
import { useLocation } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { requestLogin, requestGoogleOauthToken } from 'apis/request/auth';
import { requestUserInfo } from 'apis/request/user';
import { GoogleSVG } from 'assets/svg';
import Modal from 'components/Modal';
import { QUERY_KEY } from 'constants/key';
import { GUIDE_MESSAGE } from 'constants/message';
import useAuth from 'hooks/useAuth';
import useHandleError from 'hooks/useHandleError';
import useModal from 'hooks/useModal';
import useSnackbar from 'hooks/useSnackbar';
import { modalState } from 'store/states';
import { prevLocationProvider } from 'utils/location';

import * as S from './index.styled';

function Login() {
  const queryClient = useQueryClient();

  const { setAuth, setLogin } = useAuth();

  const userIdRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);

  const modalFlag = useRecoilValue(modalState);
  const { setOffModal, showSignupModal } = useModal();

  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();

  const location = useLocation();

  const login = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!userIdRef.current || !passwordRef.current) return;

    const userId = userIdRef.current.value;
    const password = passwordRef.current.value;

    requestLogin({ userId, password })
      .then(token => {
        setMessage(GUIDE_MESSAGE.AUTH.LOGIN_SUCCESS);
        setAuth(token);

        requestUserInfo().then(userInfo => {
          setLogin('basic', userInfo);
          queryClient.invalidateQueries([QUERY_KEY.GROUP_DETAILS]);
          queryClient.invalidateQueries([QUERY_KEY.GROUP_SUMMARIES]);
        });

        setOffModal();
      })
      .catch(error => {
        handleError(error);
      });
  };

  const googleLogin = () => {
    requestGoogleOauthToken()
      .then(oauthLink => {
        prevLocationProvider.set(location.pathname);

        window.location.assign(oauthLink);
      })
      .catch(error => {
        handleError(error);
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
