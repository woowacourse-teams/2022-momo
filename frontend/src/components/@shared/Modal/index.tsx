import React from 'react';

import Portal from 'components/@shared/Portal';
import useClosingState from 'hooks/useClosingState';
import { preventBubbling } from 'utils/event';

import * as S from './index.styled';

interface ModalProps {
  modalState: boolean;
  setOffModal: () => void;
  children: React.ReactNode;
}

const modalAnimationTime = 300;

function Modal({ modalState, setOffModal, children }: ModalProps) {
  const { isClosing, close } = useClosingState(modalAnimationTime, setOffModal);

  return (
    <Portal to="modal">
      {modalState && (
        <S.Dimmer
          onClick={close}
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
