import { getCategory } from 'apis/request/category';
import { QUERY_KEY } from 'constants/key';
import { categoryState } from 'store/states';
import { CategoryType } from 'types/data';

import useRecoilQuery from './useRecoilQuery';

const useCategory = () => {
  const { state: categories, isLoading } = useRecoilQuery(
    categoryState,
    QUERY_KEY.CATEGORY,
    getCategory,
  );

  const getCategoryDescription = (categoryId: CategoryType['id']) => {
    const categoryName = categories.find(
      category => category.id === categoryId,
    )?.name;

    return categoryName
      ? `${categoryName} 모임을 찾아봤어요 🚀`
      : '이런 모임 어때요?';
  };

  return { categories, isLoading, getCategoryDescription };
};

export default useCategory;
