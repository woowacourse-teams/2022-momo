import { useEffect, useState } from 'react';

import { useQuery, useQueryClient } from 'react-query';

import { requestGroups } from 'apis/request/group';
import ErrorBoundary from 'components/ErrorBoundary';
import { CategoryFallback } from 'components/ErrorBoundary/Fallback/Category';
import TopButton from 'components/TopButton';
import { QUERY_KEY } from 'constants/key';
import useCategory from 'hooks/useCategory';
import { CategoryType, GroupList } from 'types/data';
import { accessTokenProvider } from 'utils/token';

import Category from './Category';
import * as S from './index.styled';
import RecommendGroups from './RecommendGroups';
import SearchSection from './SearchSection';

const invalidCategoryId = -1;

function Main() {
  const { getCategoryDescription } = useCategory();
  const queryClient = useQueryClient();

  const [isExcludeFinished, setIsExcludeFinished] = useState(false);
  const [keyword, setKeyword] = useState('');
  const [selectedCategoryId, setSelectedCategoryId] =
    useState(invalidCategoryId);

  const [groups, setGroups] = useState<GroupList['groups']>([]);
  const [pageNumber, setPageNumber] = useState(0);
  const { isFetching, data, refetch } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    requestGroups(pageNumber, isExcludeFinished, keyword, selectedCategoryId),
    {
      suspense: true,
    },
  );

  useEffect(() => {
    queryClient.invalidateQueries([QUERY_KEY.GROUP_SUMMARIES]);
  }, [accessTokenProvider.get()]);

  useEffect(() => {
    if (!data) return;

    if (data.hasNextPage) {
      setPageNumber(data.pageNumber + 1);
    }

    if (data.pageNumber === 0) {
      setGroups(data.groups);
      return;
    }

    setGroups(prevState => [...prevState, ...data.groups]);
  }, [data]);

  const toggleIsExcludeFinished = async () => {
    await setIsExcludeFinished(prevState => !prevState);
    await setPageNumber(0);
    refetch();
  };

  const search = async (keyword: string) => {
    await setKeyword(keyword);
    await setPageNumber(0);
    refetch();
  };

  const selectCategory = (id: CategoryType['id']) => async () => {
    await setSelectedCategoryId(id);
    await setPageNumber(0);
    refetch();
  };

  const resetSelectedCategoryId = () => {
    selectCategory(invalidCategoryId)();
  };

  return (
    <>
      <SearchSection search={search} />
      <ErrorBoundary fallbackUI={<CategoryFallback />}>
        <Category
          selectedCategoryId={selectedCategoryId}
          selectCategory={selectCategory}
          resetSelectedCategoryId={resetSelectedCategoryId}
        />
      </ErrorBoundary>
      <S.Content>
        <ErrorBoundary>
          <RecommendGroups
            description={getCategoryDescription(selectedCategoryId)}
            isFetching={isFetching}
            data={data}
            refetch={refetch}
            groups={groups}
            isExcludeFinished={isExcludeFinished}
            toggleIsExcludeFinished={toggleIsExcludeFinished}
          />
        </ErrorBoundary>
      </S.Content>
      <TopButton />
    </>
  );
}

export default Main;
