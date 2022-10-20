import { atom, DefaultValue, selector } from 'recoil';

import { ModalStateType, SnackbarState } from 'types/condition';
import { CategoryType, GroupDetailData } from 'types/data';
import { LoginState } from 'types/user';
import { accessTokenProvider, refreshTokenProvider } from 'utils/token';

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
  default: { type: 'basic', isShowing: false, message: '' },
});

const loginState = atom<LoginState>({
  key: 'loginState',
  default: { isLogin: false },
});

const groupDetailState = atom<GroupDetailData>({
  key: 'groupDetailState',
  default: {
    name: '',
    host: {
      id: -1,
      name: '',
    },
    categoryId: 0,
    capacity: 1,
    duration: {
      start: '',
      end: '',
    },
    schedules: [],
    finished: false,
    deadline: '',
    like: false,
    location: {
      address: '',
      buildingName: '',
      detail: '',
    },
    description: '',
    imageUrl: '',
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
  categoryState,
  modalState,
  snackbarState,
  loginState,
  groupDetailState,
  accessTokenState,
  refreshTokenState,
};
