import useCategory from 'hooks/useCategory';

import * as S from './index.styled';

function Category() {
  const { categories, isError } = useCategory();

  if (isError) throw new Error();

  return (
    <S.Box>
      {categories.map(({ id, name }) => (
        <S.Button type="button" key={id}>
          <p>{name}</p>
        </S.Button>
      ))}
    </S.Box>
  );
}

export default Category;
