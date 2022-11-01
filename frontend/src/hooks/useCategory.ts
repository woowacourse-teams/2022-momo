import { useRecoilValue } from 'recoil';

import { categoryState } from 'store/states';
import { CategoryType } from 'types/data';

const useCategory = (): CategoryType[] => {
  const categories = useRecoilValue(categoryState);

  return categories;
};

export default useCategory;
