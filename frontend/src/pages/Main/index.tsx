import TopButton from 'components/@shared/TopButton';
import Category from 'components/Category';
import ErrorBoundary from 'components/ErrorBoundary';
import { CategoryFallback } from 'components/ErrorBoundary/CategoryFallback';
import RecommendGroups from 'components/RecommendGroups';
import SearchSection from 'components/SearchSection';

import * as S from './index.styled';

function Main() {
  return (
    <>
      <SearchSection />
      <ErrorBoundary fallbackUI={<CategoryFallback />}>
        <Category />
      </ErrorBoundary>
      <S.Content>
        <ErrorBoundary>
          <RecommendGroups />
        </ErrorBoundary>
      </S.Content>
      <TopButton />
    </>
  );
}

export default Main;
