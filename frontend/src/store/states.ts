import { atom } from 'recoil';

import { CategoryType } from 'types/data';

const categoryState = atom<CategoryType[]>({
  key: 'categoryState',
  default: [],
});

export { categoryState };
