import { useEffect } from 'react';

import Portal from 'components/Portal';
import { SNACKBAR_ANIMATION_TIME } from 'constants/rule';
import useClosingState from 'hooks/useClosingState';
import useSnackbar from 'hooks/useSnackbar';

import * as S from './index.styled';

interface SnackbarProps {
  children: string;
}

function Snackbar({ children }: SnackbarProps) {
  const { resetSnackbar } = useSnackbar();
  const { isClosing, close } = useClosingState(SNACKBAR_ANIMATION_TIME, () => {
    resetSnackbar();
  });

  useEffect(() => {
    const timeout = setTimeout(() => {
      close();
    }, SNACKBAR_ANIMATION_TIME * 3);

    return () => clearTimeout(timeout);
  }, [children, close]);

  return (
    <Portal to="snackbar">
      <S.Container
        animationTime={SNACKBAR_ANIMATION_TIME}
        className={isClosing ? 'close' : ''}
      >
        {children}
      </S.Container>
    </Portal>
  );
}

export default Snackbar;
