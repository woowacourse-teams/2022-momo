import { requestCategory } from 'apis/request/category';
import { QUERY_KEY } from 'constants/key';
import { categoryState } from 'store/states';

import useRecoilQuery from './useRecoilQuery';

const useCategory = () => {
  const { state: categories } = useRecoilQuery(
    categoryState,
    QUERY_KEY.CATEGORY,
    requestCategory,
  );

  return categories;
};

export default useCategory;
