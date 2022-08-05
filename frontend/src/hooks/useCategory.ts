import { getCategory } from 'apis/request/category';
import { QUERY_KEY } from 'constants/key';
import { categoryState } from 'store/states';

import useRecoilQuery from './useRecoilQuery';

const useCategory = () => {
  const { state: categories, isLoading } = useRecoilQuery(
    categoryState,
    QUERY_KEY.CATEGORY,
    getCategory,
  );

  return { categories, isLoading };
};

export default useCategory;
