import { useEffect, useState } from 'react';

import { useQuery, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { requestGroups } from 'apis/request/group';
import ErrorBoundary from 'components/ErrorBoundary';
import TopButton from 'components/TopButton';
import { QUERY_KEY } from 'constants/key';
import { BROWSER_PATH } from 'constants/path';
import useMount from 'hooks/useMount';
import { CategoryType, GroupList } from 'types/data';
import { accessTokenProvider } from 'utils/token';

import FilterSection from './FilterSection';
import * as S from './index.styled';
import WholeGroups from './WholeGroups';

const invalidCategoryId = -1;

function Main() {
  const queryClient = useQueryClient();

  const [isExcludeFinished, setIsExcludeFinished] = useState(true);
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

  const navigate = useNavigate();

  useMount(() => {
    const isVisitedUser = localStorage.getItem('visited');

    if (!isVisitedUser) {
      navigate(BROWSER_PATH.LANDING);
    }

    localStorage.setItem('visited', 'true');
  });

  useEffect(() => {
    queryClient.invalidateQueries([QUERY_KEY.GROUP_SUMMARIES]);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [accessTokenProvider.get()]);

  useEffect(() => {
    if (!data) return;

    const { pageNumber, hasNextPage, groups } = data;

    if (hasNextPage) {
      setPageNumber(pageNumber + 1);
    }

    if (pageNumber === 0) {
      setGroups(groups);
      return;
    }

    setGroups(prevState => [...prevState, ...groups]);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isFetching]);

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
      <FilterSection
        search={search}
        selectedCategoryId={selectedCategoryId}
        selectCategory={selectCategory}
        resetSelectedCategoryId={resetSelectedCategoryId}
        isExcludeFinished={isExcludeFinished}
        toggleIsExcludeFinished={toggleIsExcludeFinished}
      />
      <S.Content>
        <ErrorBoundary>
          <WholeGroups
            isFetching={isFetching}
            data={data}
            refetch={refetch}
            groups={groups}
          />
        </ErrorBoundary>
        <TopButton />
      </S.Content>
    </>
  );
}

export default Main;
