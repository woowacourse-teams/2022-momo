import { atom, DefaultValue, selector } from 'recoil';

import { ModalStateType, SnackbarState } from 'types/condition';
import { CategoryType } from 'types/data';
import { LoginState } from 'types/user';
import { accessTokenProvider } from 'utils/token';

const categoryState = atom<CategoryType[]>({
  key: 'categoryState',
  default: [],
});

const modalState = atom<ModalStateType>({
  key: 'modalState',
  default: 'off',
});

const snackbarState = atom<SnackbarState>({
  key: 'snackbarState',
  default: { isShowing: false, message: '' },
});

const loginState = atom<LoginState>({
  key: 'loginState',
  default: { isLogin: false },
});

const accessTokenState = selector<string>({
  key: 'accessToken',
  get: () => {
    return accessTokenProvider.get();
  },
  set: (_, accessToken) => {
    if (!accessToken) {
      accessTokenProvider.remove();
    }

    if (!(accessToken instanceof DefaultValue)) {
      accessTokenProvider.set(accessToken);
    }
  },
});

export {
  categoryState,
  modalState,
  snackbarState,
  loginState,
  accessTokenState,
};
