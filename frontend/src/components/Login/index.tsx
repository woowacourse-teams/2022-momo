import { useRecoilState, useSetRecoilState } from 'recoil';

import { requestLogin } from 'apis/request/auth';
import Modal from 'components/Modal';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useInput from 'hooks/useInput';
import { accessTokenState, modalState } from 'store/states';

import * as S from './index.styled';

function Login() {
  const [modalFlag, setModalFlag] = useRecoilState(modalState);
  const setAccessToken = useSetRecoilState(accessTokenState);
  const { value: email, setValue: setEmail } = useInput('');
  const { value: password, setValue: setPassword } = useInput('');

  const setOffModal = () => {
    setModalFlag('off');
  };

  // TODO: 비제어 컴포넌트로 수정하기
  const login = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    requestLogin({ email, password })
      .then(accessToken => {
        alert(GUIDE_MESSAGE.AUTH.LOGIN_SUCCESS);

        setAccessToken(accessToken);
        setOffModal();
      })
      .catch(() => {
        alert(ERROR_MESSAGE.AUTH.FAILURE_LOGIN_REQUEST);
      });
  };

  return (
    <Modal modalState={modalFlag === 'login'} setOffModal={setOffModal}>
      <S.Form onSubmit={login}>
        <S.Title>로그인</S.Title>
        <S.InputContainer>
          <S.Label>
            이메일
            <S.Input
              type="email"
              value={email}
              onChange={setEmail}
              placeholder="brie@woowa.net"
              required
            />
          </S.Label>
          <S.Label>
            비밀번호
            <S.Input
              type="password"
              value={password}
              onChange={setPassword}
              placeholder="********"
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
