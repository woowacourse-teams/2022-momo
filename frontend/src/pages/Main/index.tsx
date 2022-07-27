import Category from 'components/Category';
import ErrorBoundary from 'components/ErrorBoundary';
import RecommendGroups from 'components/RecommendGroups';
import Search from 'components/Search';

import * as S from './index.styled';

function Main() {
  return (
    <>
      <Search />
      <ErrorBoundary>
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
