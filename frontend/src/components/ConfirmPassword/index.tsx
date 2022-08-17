import React from 'react';

import { useRecoilValue } from 'recoil';

import Modal from 'components/@shared/Modal';
import useModal from 'hooks/useModal';
import { modalState } from 'store/states';

import * as S from './index.styled';

interface ConfirmPasswordProps {
  confirmPassword: string;
  setConfirmPassword: (e: React.ChangeEvent<HTMLInputElement>) => void;
  editPassword: () => void;
}

function ConfirmPassword({
  confirmPassword,
  setConfirmPassword,
  editPassword,
}: ConfirmPasswordProps) {
  const modalFlag = useRecoilValue(modalState);
  const { setOffModal } = useModal();

  const edit = () => {
    editPassword();
    setOffModal();
  };

  return (
    <Modal modalState={modalFlag === 'confirmPassword'}>
      <S.Container>
        <S.Title>이전 비밀번호 입력</S.Title>
        <S.Input
          type="password"
          placeholder="********"
          value={confirmPassword}
          onChange={setConfirmPassword}
          required
        />
        <S.Button type="button" onClick={edit}>
          비밀번호 확인
        </S.Button>
      </S.Container>
    </Modal>
  );
}

export default ConfirmPassword;
