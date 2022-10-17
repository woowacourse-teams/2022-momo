import { useSetRecoilState, useResetRecoilState } from 'recoil';

import { snackbarState } from 'store/states';

const useSnackbar = () => {
  const setSnackbar = useSetRecoilState(snackbarState);
  const resetSnackbar = useResetRecoilState(snackbarState);

  const setMessage = (message: string, isError: boolean = false) => {
    setSnackbar({
      type: isError ? 'error' : 'basic',
      isShowing: true,
      message,
    });
  };

  return { setMessage, resetSnackbar };
};

export default useSnackbar;
