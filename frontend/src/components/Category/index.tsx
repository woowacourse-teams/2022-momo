import useCategory from 'hooks/useCategory';

import * as S from './index.styled';

function Category() {
  const { categories, isLoading, isError } = useCategory();

  if (isLoading) return <h2>카테고리 로딩 중...</h2>;

  if (isError) return <h2>에러 발생</h2>;

  return (
    <S.Box>
      {categories.map(({ id, name }) => (
        <S.Button type="button" key={id}>
          {name}
        </S.Button>
      ))}
    </S.Box>
  );
}

export default Category;
