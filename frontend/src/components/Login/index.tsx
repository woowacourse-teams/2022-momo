import { useRef } from 'react';

import { useRecoilState, useSetRecoilState } from 'recoil';

import { requestLogin } from 'apis/request/auth';
import Modal from 'components/Modal';
import { GUIDE_MESSAGE } from 'constants/message';
import { accessTokenState, loginState, modalState } from 'store/states';
import { showErrorMessage } from 'utils/errorController';

import * as S from './index.styled';

function Login() {
  const [modalFlag, setModalFlag] = useRecoilState(modalState);
  const setAccessToken = useSetRecoilState(accessTokenState);
  const setIsLogin = useSetRecoilState(loginState);
  const userIdRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);

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
        alert(GUIDE_MESSAGE.AUTH.LOGIN_SUCCESS);

        setAccessToken(accessToken);
        setIsLogin(true);
        setOffModal();
      })
      .catch(({ message }) => {
        alert(showErrorMessage(message));
      });
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
        <S.Button type="submit">로그인</S.Button>
      </S.Form>
    </Modal>
  );
}

export default Login;
