import { useEffect, useState } from 'react';

import { useRecoilState } from 'recoil';

import { requestSignup } from 'apis/request/auth';
import Modal from 'components/Modal';
import { GUIDE_MESSAGE } from 'constants/message';
import useInput from 'hooks/useInput';
import { modalState } from 'store/states';
import { showErrorMessage } from 'utils/errorController';

import * as S from './index.styled';
import {
  checkValidNickname,
  checkValidPassword,
  isValidSignupFormData,
} from './validate';

function Signup() {
  const [isModalOpen, setModalState] = useRecoilState(modalState);
  const { value: userId, setValue: setUserId } = useInput('');
  const { value: name, setValue: setName } = useInput('');
  const { value: password, setValue: setPassword } = useInput('');
  const { value: confirmPassword, setValue: setConfirmPassword } = useInput('');
  const [isValidName, setIsValidName] = useState(true);
  const [isValidPassword, setIsValidPassword] = useState(true);
  const [isValidConfirmPassword, setIsValidConfirmPassword] = useState(true);

  const setOffModal = () => {
    setModalState('off');
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

    requestSignup({ userId, password, name })
      .then(() => {
        alert(GUIDE_MESSAGE.AUTH.SIGNUP_SUCCESS);
        setOffModal();
      })
      .catch(({ message }) => {
        alert(showErrorMessage(message));
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
    <Modal modalState={isModalOpen === 'signup'} setOffModal={setOffModal}>
      <S.Form onSubmit={signup}>
        <S.Title>회원가입</S.Title>
        <S.InputContainer>
          <S.Label>
            아이디
            <S.Input
              type="text"
              placeholder="brie"
              value={userId}
              onChange={setUserId}
              required
            />
          </S.Label>
          <S.Label>
            닉네임
            <S.Input
              type="text"
              placeholder="사용하실 닉네임을 입력해주세요."
              value={name}
              onChange={setName}
              required
            />
            <S.InfoMessage isValid={isValidName}>
              닉네임은 1~6자 사이여야 합니다.
            </S.InfoMessage>
          </S.Label>
          <S.Label>
            비밀번호
            <S.Input
              type="password"
              placeholder="영문자, 숫자, 특수문자 포함 8자 이상"
              value={password}
              onChange={setPassword}
              required
            />
            <S.InfoMessage isValid={isValidPassword}>
              비밀번호는 8~16자 사이의 영문, 숫자, 특수문자의 조합이어야 합니다.
            </S.InfoMessage>
          </S.Label>
          <S.Label>
            비밀번호 확인
            <S.Input
              type="password"
              placeholder="비밀번호를 한 번 더 입력해주세요."
              value={confirmPassword}
              onChange={setConfirmPassword}
              required
            />
            {isValidConfirmPassword || (
              <S.InfoMessage>비밀번호 확인이 일치하지 않습니다.</S.InfoMessage>
            )}
          </S.Label>
        </S.InputContainer>
        <S.Button type="submit">회원가입</S.Button>
      </S.Form>
    </Modal>
  );
}

export default Signup;
