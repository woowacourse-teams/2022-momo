import Portal from 'components/Portal';
import useClosingState from 'hooks/useClosingState';
import useModal from 'hooks/useModal';
import { preventBubbling } from 'utils/event';

import * as S from './index.styled';

interface ModalProps {
  modalState: boolean;
  children: React.ReactNode;
}

const modalAnimationTime = 150;

function Modal({ modalState, children }: ModalProps) {
  const { setOffModal } = useModal();
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
