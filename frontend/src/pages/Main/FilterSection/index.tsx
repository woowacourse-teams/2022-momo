import Checkbox from 'components/Checkbox';
import ErrorBoundary from 'components/ErrorBoundary';
import { CategoryFallback } from 'components/ErrorBoundary/Fallback/Category';
import { CategoryType } from 'types/data';

import Category from '../Category';
import SearchSection from '../SearchSection';
import * as S from './index.styled';

interface FilterSectionProps {
  search: (keyword: string) => void;
  selectedCategoryId: CategoryType['id'];
  selectCategory: (id: CategoryType['id']) => () => void;
  resetSelectedCategoryId: () => void;
  isExcludeFinished: boolean;
  toggleIsExcludeFinished: () => void;
  children?: React.ReactNode;
}

function FilterSection({
  search,
  selectedCategoryId,
  selectCategory,
  resetSelectedCategoryId,
  isExcludeFinished,
  toggleIsExcludeFinished,
  children,
}: FilterSectionProps) {
  return (
    <S.Container>
      <S.ContentContainer>
        <SearchSection search={search} />
        <ErrorBoundary fallbackUI={<CategoryFallback />}>
          <Category
            selectedCategoryId={selectedCategoryId}
            selectCategory={selectCategory}
            resetSelectedCategoryId={resetSelectedCategoryId}
          />
        </ErrorBoundary>
        {children}
        <Checkbox
          description="마감된 모임 제외"
          checked={isExcludeFinished}
          toggleChecked={toggleIsExcludeFinished}
        />
      </S.ContentContainer>
    </S.Container>
  );
}

export default FilterSection;
