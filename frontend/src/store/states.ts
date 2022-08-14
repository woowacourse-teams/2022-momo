import { atom, DefaultValue, selector } from 'recoil';

import { ModalStateType, SnackbarState } from 'types/condition';
import { CategoryType } from 'types/data';
import { LoginState } from 'types/user';

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
    return sessionStorage.getItem('accessToken') ?? '';
  },
  set: (_, accessToken) => {
    if (!accessToken) {
      sessionStorage.removeItem('accessToken');
    }

    if (!(accessToken instanceof DefaultValue)) {
      sessionStorage.setItem('accessToken', accessToken);
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
