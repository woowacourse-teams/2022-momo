import useCategory from 'hooks/useCategory';
import { CategoryType } from 'types/data';

import * as S from './index.styled';

interface CategoryProps {
  selectedCategoryId: CategoryType['id'];
  selectCategory: (id: CategoryType['id']) => () => void;
  resetSelectedCategoryId: () => void;
}

function Category({
  selectedCategoryId,
  selectCategory,
  resetSelectedCategoryId,
}: CategoryProps) {
  const { categories } = useCategory();

  return (
    <S.Box>
      {categories.map(({ id, name }) => {
        const isSelected = selectedCategoryId === id;

        return (
          <S.Button
            type="button"
            onClick={isSelected ? resetSelectedCategoryId : selectCategory(id)}
            className={isSelected ? 'select' : ''}
            key={id}
          >
            <p>{name}</p>
          </S.Button>
        );
      })}
    </S.Box>
  );
}

export default Category;
