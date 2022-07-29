import React, { useState } from 'react';

import Portal from 'components/Portal';
import { preventBubbling } from 'utils/event';

import * as S from './index.styled';

interface ModalProps {
  modalState: boolean;
  setOffModal: () => void;
  children: React.ReactNode;
}

function Modal({ modalState, setOffModal, children }: ModalProps) {
  const [isClosing, setIsClosing] = useState(false);

  const setOffModalWithAnimation = () => {
    setIsClosing(true);

    setTimeout(() => {
      setOffModal();
      setIsClosing(false);
    }, 300);
  };

  return (
    <Portal>
      {modalState && (
        <S.Dimmer
          onClick={setOffModalWithAnimation}
          className={isClosing ? 'close' : ''}
        >
          <S.Content
            onClick={preventBubbling}
            className={isClosing ? 'close' : ''}
          >
            {children}
          </S.Content>
        </S.Dimmer>
      )}
    </Portal>
  );
}

export default Modal;
