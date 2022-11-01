import { useSetRecoilState, useResetRecoilState, Resetter } from 'recoil';

import { snackbarState } from 'store/states';

interface UseSnackBarReturnType {
  setMessage: (message: string, isError?: boolean) => void;
  resetSnackbar: Resetter;
}

const useSnackbar = (): UseSnackBarReturnType => {
  const setSnackbar = useSetRecoilState(snackbarState);
  const resetSnackbar = useResetRecoilState(snackbarState);

  const setMessage = (message: string, isError: boolean = false): void => {
    setSnackbar({
      type: isError ? 'error' : 'basic',
      isShowing: true,
      message,
    });
  };

  return { setMessage, resetSnackbar };
};

export default useSnackbar;
