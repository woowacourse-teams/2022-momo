import * as S from './index.styled';

const categories = [
  '운동',
  '스터디',
  '한 잔',
  '영화',
  '모각코',
  '맛집',
  '카페',
  '쇼핑',
  '등산',
  '문화생활',
];

function Category() {
  return (
    <S.CategoryContainer>
      {categories.map(category => (
        <S.Category type="button" key={category}>
          {category}
        </S.Category>
      ))}
    </S.CategoryContainer>
  );
}

export default Category;
