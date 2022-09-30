import { useEffect, useRef, useState } from 'react';

import { useRecoilValue } from 'recoil';

import { requestSignup } from 'apis/request/auth';
import Modal from 'components/Modal';
import { GUIDE_MESSAGE } from 'constants/message';
import useHandleError from 'hooks/useHandleError';
import useInput from 'hooks/useInput';
import useModal from 'hooks/useModal';
import useSnackbar from 'hooks/useSnackbar';
import { modalState } from 'store/states';

import * as S from './index.styled';
import {
  checkValidNickname,
  checkValidPassword,
  isValidSignupFormData,
} from './validate';

function Signup() {
  const modalFlag = useRecoilValue(modalState);
  const { showLoginModal } = useModal();
  const { handleError } = useHandleError();

  const idRef = useRef<HTMLInputElement>(null);
  const {
    value: name,
    setValue: setName,
    dangerouslySetValue: dangerouslySetName,
  } = useInput('');
  const {
    value: password,
    setValue: setPassword,
    dangerouslySetValue: dangerouslySetPassword,
  } = useInput('');
  const {
    value: confirmPassword,
    setValue: setConfirmPassword,
    dangerouslySetValue: dangerouslySetConfirmPassword,
  } = useInput('');
  const [isValidName, setIsValidName] = useState(true);
  const [isValidPassword, setIsValidPassword] = useState(true);
  const [isValidConfirmPassword, setIsValidConfirmPassword] = useState(true);

  const { setMessage } = useSnackbar();

  const resetValues = () => {
    if (!idRef.current) return;

    idRef.current.value = '';
    dangerouslySetName('');
    dangerouslySetPassword('');
    dangerouslySetConfirmPassword('');
  };

  const signup = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      isValidSignupFormData({
        isValidName,
        isValidPassword,
        isValidConfirmPassword,
      });
    } catch ({ message }) {
      alert(message);
      return;
    }

    if (!idRef.current) return;

    requestSignup({ userId: idRef.current.value, password, name })
      .then(() => {
        setMessage(GUIDE_MESSAGE.AUTH.SIGNUP_SUCCESS);
        resetValues();
        showLoginModal();
      })
      .catch(error => {
        handleError(error);
      });
  };

  useEffect(() => {
    setIsValidPassword(password.length === 0 || checkValidPassword(password));
    setIsValidConfirmPassword(
      confirmPassword.length === 0 || password === confirmPassword,
    );
  }, [password, confirmPassword, isValidPassword]);

  useEffect(() => {
    setIsValidName(name.length === 0 || checkValidNickname(name));
  }, [name]);

  return (
    <Modal modalState={modalFlag === 'signup'}>
      <S.Form onSubmit={signup}>
        <S.Title>회원가입</S.Title>
        <S.InputContainer>
          <S.Label>
            아이디
            <S.Input type="text" ref={idRef} autoFocus required />
          </S.Label>
          <S.Label>
            닉네임
            <S.Input type="text" value={name} onChange={setName} required />
            <S.InfoMessage isValid={isValidName}>
              닉네임은 1~6자 사이여야 해요.
            </S.InfoMessage>
          </S.Label>
          <S.Label>
            비밀번호
            <S.Input
              type="password"
              value={password}
              onChange={setPassword}
              required
            />
            <S.InfoMessage isValid={isValidPassword}>
              8~16자 사이의 영문, 숫자, 특수문자 포함
            </S.InfoMessage>
          </S.Label>
          <S.Label>
            비밀번호 확인
            <S.Input
              type="password"
              value={confirmPassword}
              onChange={setConfirmPassword}
              required
            />
            <S.WarningMessage isValid={isValidConfirmPassword}>
              비밀번호 확인이 일치하지 않아요.
            </S.WarningMessage>
          </S.Label>
        </S.InputContainer>
        <S.Button type="submit">회원가입</S.Button>
      </S.Form>
    </Modal>
  );
}

export default Signup;
