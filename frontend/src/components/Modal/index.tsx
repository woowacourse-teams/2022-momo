import React, { useState } from 'react';

import Portal from 'components/Portal';
import { preventBubbling } from 'utils/event';

import * as S from './index.styled';

interface ModalProps {
  modalState: boolean;
  setOffModal: () => void;
  children: React.ReactNode;
}

const modalAnimationTime = 300;

function Modal({ modalState, setOffModal, children }: ModalProps) {
  const [isClosing, setIsClosing] = useState(false);

  const setOffModalWithAnimation = () => {
    setIsClosing(true);

    setTimeout(() => {
      setOffModal();
      setIsClosing(false);
    }, modalAnimationTime);
  };

  return (
    <Portal>
      {modalState && (
        <S.Dimmer
          onClick={setOffModalWithAnimation}
          className={isClosing ? 'close' : ''}
          animationTime={modalAnimationTime}
        >
          <S.Content
            onClick={preventBubbling}
            className={isClosing ? 'close' : ''}
            animationTime={modalAnimationTime}
          >
            {children}
          </S.Content>
        </S.Dimmer>
      )}
    </Portal>
  );
}

export default Modal;
