import Category from 'components/Category';
import ErrorBoundary from 'components/ErrorBoundary';
import { CategoryFallback } from 'components/ErrorBoundary/CategoryFallback';
import RecommendGroups from 'components/RecommendGroups';
import Search from 'components/Search';

import * as S from './index.styled';

function Main() {
  return (
    <>
      <Search />
      <ErrorBoundary fallbackUI={<CategoryFallback />}>
        <Category />
      </ErrorBoundary>
      <S.Content>
        <ErrorBoundary>
          <RecommendGroups />
        </ErrorBoundary>
      </S.Content>
    </>
  );
}

export default Main;
