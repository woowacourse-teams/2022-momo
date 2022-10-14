import {
  BackwardArrowSVG,
  BeanSVG,
  CafeSVG,
  CultureSVG,
  DrinkSVG,
  ExerciseSVG,
  ForwardArrowSVG,
  GameSVG,
  GuitarSVG,
  MogackoSVG,
  SicsaSVG,
  StudySVG,
  TravelSVG,
} from 'assets/svg';
import useCategory from 'hooks/useCategory';
import { CategoryType } from 'types/data';

import * as S from './index.styled';

const svgSize = 40;
const categoryIcon = [
  { icon: <StudySVG width={svgSize} height={svgSize} /> },
  { icon: <MogackoSVG width={svgSize} height={svgSize} /> },
  { icon: <SicsaSVG width={svgSize} height={svgSize} /> },
  { icon: <CafeSVG width={svgSize} height={svgSize} /> },
  { icon: <DrinkSVG width={svgSize} height={svgSize} /> },
  { icon: <ExerciseSVG width={svgSize} height={svgSize} /> },
  { icon: <GameSVG width={svgSize} height={svgSize} /> },
  { icon: <TravelSVG width={svgSize} height={svgSize} /> },
  { icon: <CultureSVG width={svgSize} height={svgSize} /> },
  { icon: <GuitarSVG width={svgSize} height={svgSize} /> },
];
const categorySVG = (id: number) => categoryIcon[id - 1].icon;
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
                {categorySVG(id)}
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
