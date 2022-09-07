import { useEffect, useState } from 'react';

import { useQuery } from 'react-query';

import { requestGroups } from 'apis/request/group';
import ErrorBoundary from 'components/@shared/ErrorBoundary';
import { CategoryFallback } from 'components/@shared/ErrorBoundary/CategoryFallback';
import TopButton from 'components/@shared/TopButton';
import Category from 'components/Category';
import RecommendGroups from 'components/RecommendGroups';
import SearchSection from 'components/SearchSection';
import { QUERY_KEY } from 'constants/key';
import useCategory from 'hooks/useCategory';
import useInput from 'hooks/useInput';
import { CategoryType, GroupList } from 'types/data';

import * as S from './index.styled';

const invalidCategoryId = -1;

function Main() {
  const { getCategoryDescription } = useCategory();

  const [isExcludeFinished, setIsExcludeFinished] = useState(false);
  const { value: keyword, setValue: setKeyword } = useInput('');
  const [selectedCategoryId, setSelectedCategoryId] =
    useState(invalidCategoryId);

  const [pageNumber, setPageNumber] = useState(0);
  const { isFetching, data, refetch } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    requestGroups(pageNumber, isExcludeFinished, keyword, selectedCategoryId),
    {
      suspense: true,
    },
  );

  const [groups, setGroups] = useState<GroupList['groups']>([]);

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

  const search = async () => {
    await setPageNumber(0);
    refetch();
  };

  const selectCategory = (id: CategoryType['id']) => async () => {
    await setSelectedCategoryId(id);
    await setPageNumber(0);
    // hotfix : 2022-08-19
    await setIsExcludeFinished(false);
    refetch();
  };

  const resetSelectedCategoryId = () => {
    selectCategory(invalidCategoryId)();
  };

  return (
    <>
      <SearchSection
        keyword={keyword}
        setKeyword={setKeyword}
        search={search}
      />
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
            selectedCategoryId={selectedCategoryId}
          />
        </ErrorBoundary>
      </S.Content>
      <TopButton />
    </>
  );
}

export default Main;
