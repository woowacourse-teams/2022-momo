import React, { useRef } from 'react';

import { useRecoilValue } from 'recoil';

import { requestConfirmPassword } from 'apis/request/user';
import Modal from 'components/@shared/Modal';
import { ERROR_MESSAGE } from 'constants/message';
import useModal from 'hooks/useModal';
import { modalState } from 'store/states';

import * as S from './index.styled';

interface ConfirmPasswordProps {
  editValue: () => void;
}

function ConfirmPassword({ editValue }: ConfirmPasswordProps) {
  const modalFlag = useRecoilValue(modalState);
  const { setOffModal } = useModal();

  const confirmPasswordRef = useRef<HTMLInputElement>(null);

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
    <Modal modalState={modalFlag === 'confirmPassword'}>
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
