import React, { useRef } from 'react';

import { useRecoilState } from 'recoil';

import {
  requestChangeName,
  requestChangePassword,
  requestConfirmPassword,
} from 'apis/request/user';
import Modal from 'components/@shared/Modal';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useSnackbar from 'hooks/useSnackbar';
import { modalState } from 'store/states';

import * as S from './index.styled';

interface ConfirmPasswordProps {
  type: string;
  newValue: string;
  setIsEditable: React.Dispatch<React.SetStateAction<boolean>>;
}

function ConfirmPassword({
  type,
  newValue,
  setIsEditable,
}: ConfirmPasswordProps) {
  const [modalFlag, setModalFlag] = useRecoilState(modalState);

  const confirmPasswordRef = useRef<HTMLInputElement>(null);

  const { setMessage } = useSnackbar();

  const setOffModal = () => {
    setModalFlag('off');
  };

  const editValue = () => {
    switch (type) {
      case 'name':
        requestChangeName(newValue)
          .then(() => {
            setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_NAME_REQUEST);
            setIsEditable(false);
          })
          .catch(() => {
            alert(ERROR_MESSAGE.MEMBER.FAILURE_NAME_REQUEST);
          });

        break;
      case 'password':
        requestChangePassword(newValue)
          .then(() => {
            setMessage(GUIDE_MESSAGE.MEMBER.SUCCESS_PASSWORD_REQUEST);
            setIsEditable(false);
          })
          .catch(() => {
            alert(ERROR_MESSAGE.MEMBER.FAILURE_PASSWORD_REQUEST);
          });

        break;
    }
  };

  const confirmPassword = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!confirmPasswordRef.current) return;

    const confirmPassword = confirmPasswordRef.current.value;

    requestConfirmPassword(confirmPassword)
      .then(() => {
        editValue();
        setOffModal();
      })
      .catch(() => {
        alert(ERROR_MESSAGE.MEMBER.FAILURE_CONFIRM_PASSWORD_REQUEST);
      });
  };

  return (
    <Modal
      modalState={modalFlag === 'confirmPassword'}
      setOffModal={setOffModal}
    >
      <S.Form onSubmit={confirmPassword}>
        <S.Title>비밀번호 확인</S.Title>
        <S.Input
          type="password"
          placeholder="********"
          ref={confirmPasswordRef}
          required
        />
        <S.Button type="submit">비밀번호 확인</S.Button>
      </S.Form>
    </Modal>
  );
}

export default ConfirmPassword;