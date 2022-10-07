import { useEffect } from 'react';

import Portal from 'components/Portal';
import useClosingState from 'hooks/useClosingState';
import useSnackbar from 'hooks/useSnackbar';

import * as S from './index.styled';

const snackbarAnimationTime = 500;

interface SnackbarProps {
  children: string;
}

function Snackbar({ children }: SnackbarProps) {
  const { resetSnackbar } = useSnackbar();
  const { isClosing, close } = useClosingState(snackbarAnimationTime, () => {
    resetSnackbar();
  });

  useEffect(() => {
    const timeout = setTimeout(() => {
      close();
    }, snackbarAnimationTime * 3);

    return () => clearTimeout(timeout);
  }, [children, close]);

  return (
    <Portal to="snackbar">
      <S.Container
        animationTime={snackbarAnimationTime}
        className={isClosing ? 'close' : ''}
      >
        {children}
      </S.Container>
    </Portal>
  );
}

export default Snackbar;
