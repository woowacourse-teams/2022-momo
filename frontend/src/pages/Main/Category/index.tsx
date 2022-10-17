import { BackwardArrowSVG, BeanSVG, ForwardArrowSVG } from 'assets/svg';
import useCategory from 'hooks/useCategory';
import { CategoryType } from 'types/data';
import { getCategoryIcon } from 'utils/category';

import * as S from './index.styled';

const svgSize = 30;

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
  const categories = useCategory();

  return (
    <S.Container>
      <S.FloatingLeftButton>
        <BackwardArrowSVG />
      </S.FloatingLeftButton>
      <S.Box>
        <S.ButtonContainer onClick={resetSelectedCategoryId}>
          <S.Button type="button">
            <BeanSVG width={svgSize} height={svgSize} />
          </S.Button>
          <p>전체</p>
        </S.ButtonContainer>
        {categories.map(({ id, name }) => {
          const isSelected = selectedCategoryId === id;

          return (
            <S.ButtonContainer
              key={id}
              onClick={
                isSelected ? resetSelectedCategoryId : selectCategory(id)
              }
            >
              <S.Button type="button" className={isSelected ? 'select' : ''}>
                {getCategoryIcon(id, svgSize)}
              </S.Button>
              <p>{name}</p>
            </S.ButtonContainer>
          );
        })}
      </S.Box>
      <S.FloatingRightButton>
        <ForwardArrowSVG />
      </S.FloatingRightButton>
    </S.Container>
  );
}

export default Category;
