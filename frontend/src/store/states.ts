import { atom, DefaultValue, selector } from 'recoil';

import { requestCategory } from 'apis/request/category';
import { ModalStateType, SnackbarState } from 'types/condition';
import { CategoryType } from 'types/data';
import { LoginState } from 'types/user';
import { accessTokenProvider, refreshTokenProvider } from 'utils/token';

const modalState = atom<ModalStateType>({
  key: 'modalState',
  default: 'off',
});

const snackbarState = atom<SnackbarState>({
  key: 'snackbarState',
  default: { type: 'basic', isShowing: false, message: '' },
});

const loginState = atom<LoginState>({
  key: 'loginState',
  default: { isLogin: false },
});

const categoryState = selector<CategoryType[]>({
  key: 'categoryState',
  get: async () => {
    const categories = await requestCategory();

    return categories;
  },
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

const refreshTokenState = selector<string>({
  key: 'refreshToken',
  get: () => {
    return refreshTokenProvider.get();
  },
  set: (_, refreshToken) => {
    if (!refreshToken) {
      refreshTokenProvider.remove();
    }

    if (!(refreshToken instanceof DefaultValue)) {
      refreshTokenProvider.set(refreshToken);
    }
  },
});

export {
  modalState,
  snackbarState,
  loginState,
  categoryState,
  accessTokenState,
  refreshTokenState,
};
