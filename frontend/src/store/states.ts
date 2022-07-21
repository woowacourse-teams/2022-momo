import { atom, DefaultValue, selector } from 'recoil';

import { ModalStateType } from 'types/condition';
import { CategoryType } from 'types/data';

const categoryState = atom<CategoryType[]>({
  key: 'categoryState',
  default: [],
});

const modalState = atom<ModalStateType>({
  key: 'modalState',
  default: 'off',
});

const accessTokenState = selector<string>({
  key: 'accessToken',
  get: () => sessionStorage.getItem('accessToken') ?? '',
  set: (_, accessToken) => {
    if (!(accessToken instanceof DefaultValue)) {
      sessionStorage.setItem('accessToken', accessToken);
    }
  },
});

export { categoryState, modalState, accessTokenState };
