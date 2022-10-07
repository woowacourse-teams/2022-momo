import { forwardRef, LegacyRef, memo } from 'react';

import useCategory from 'hooks/useCategory';
import { CreateStateReturnValues } from 'hooks/useCreateState';
import { CreateGroupData } from 'types/data';
import { isEqualObject } from 'utils/object';

import { Container, ErrorColor, Heading } from '../@shared/styled';
import * as S from './index.styled';

interface Step2Props {
  useSelectedCategoryState: CreateStateReturnValues['useSelectedCategoryState'];
  gotoAdjacentPage: (direction: 'next' | 'prev') => void;
}

function Step2(
  { useSelectedCategoryState, gotoAdjacentPage }: Step2Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { selectedCategory, setSelectedCategory } = useSelectedCategoryState();
  const { categories } = useCategory();

  const selectCategory =
    (newSelectedCategory: CreateGroupData['selectedCategory']) => () => {
      setSelectedCategory(newSelectedCategory);

      gotoAdjacentPage('next');
    };

  return (
    <Container ref={ref}>
      <Heading>
        <span>어떤</span> 모임인가요? <ErrorColor>*</ErrorColor>
        <p>(카테고리 선택)</p>
      </Heading>
      <S.OptionBox>
        {categories &&
          categories.map(category => (
            <S.Button
              type="button"
              key={category.id}
              className={
                isEqualObject(selectedCategory, category) ? 'isActive' : ''
              }
              onClick={selectCategory(category)}
            >
              {category.name}
            </S.Button>
          ))}
      </S.OptionBox>
    </Container>
  );
}

export default memo(forwardRef(Step2));
