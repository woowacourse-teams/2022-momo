import { atom } from 'recoil';

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

export { categoryState, modalState };
