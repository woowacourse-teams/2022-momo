import React from 'react';

import Portal from 'components/Portal';
import { preventBubbling } from 'utils/event';

import * as S from './index.styled';

interface ModalProps {
  modalState: boolean;
  setOffModal: () => void;
  children: React.ReactNode;
}

function Modal({ modalState, setOffModal, children }: ModalProps) {
  return (
    <Portal>
      {modalState && (
        <S.Dimmer onClick={setOffModal}>
          <S.Content onClick={preventBubbling}>{children}</S.Content>
        </S.Dimmer>
      )}
    </Portal>
  );
}

export default Modal;
