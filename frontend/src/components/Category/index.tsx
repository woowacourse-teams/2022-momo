import { useState } from 'react';

import useCategory from 'hooks/useCategory';
import { CategoryType } from 'types/data';

import * as S from './index.styled';

function Category() {
  const { categories } = useCategory();
  const [selectedCategoryId, setSelectedCategoryId] = useState(-1);

  const selectCategory = (id: CategoryType['id']) => () => {
    setSelectedCategoryId(id);
  };

  return (
    <S.Box>
      {categories.map(({ id, name }) => (
        <S.Button
          type="button"
          onClick={selectCategory(id)}
          className={selectedCategoryId === id ? 'select' : ''}
          key={id}
        >
          <p>{name}</p>
        </S.Button>
      ))}
    </S.Box>
  );
}

export default Category;
