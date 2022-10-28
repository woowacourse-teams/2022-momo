import { useRecoilValue } from 'recoil';

import { categoryState } from 'store/states';

const useCategory = () => {
  const categories = useRecoilValue(categoryState);

  return categories;
};

export default useCategory;
