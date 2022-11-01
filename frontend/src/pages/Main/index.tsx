import { useEffect, useState } from 'react';

import { useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { requestGroups } from 'apis/request/group';
import ErrorBoundary from 'components/ErrorBoundary';
import FilterSection from 'components/FilterSection';
import TopButton from 'components/TopButton';
import { QUERY_KEY } from 'constants/key';
import { BROWSER_PATH } from 'constants/path';
import useMount from 'hooks/useMount';
import { CategoryType, GroupList } from 'types/data';

import * as S from './index.styled';
import WholeGroups from './WholeGroups';

const invalidCategoryId = -1;

function Main(): JSX.Element {
  const [pageNumber, setPageNumber] = useState(0);
  const [isExcludeFinished, setIsExcludeFinished] = useState(true);
  const [keyword, setKeyword] = useState('');
  const [selectedCategoryId, setSelectedCategoryId] =
    useState(invalidCategoryId);

  const [groups, setGroups] = useState<GroupList['groups']>([]);
  const { isFetching, dataUpdatedAt, data, refetch } = useQuery(
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
    if (!data) return;

    const { pageNumber, hasNextPage, groups: newGroups } = data;

    if (hasNextPage) {
      setPageNumber(pageNumber + 1);
    }

    if (pageNumber <= 0) {
      setGroups(newGroups);
      return;
    }

    setGroups(prevState => [...prevState, ...newGroups]);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [dataUpdatedAt]);

  const toggleIsExcludeFinished = async () => {
    await setIsExcludeFinished(prevState => !prevState);
    await setPageNumber(0);
    refetch();
  };

  const search = async (keyword: string): Promise<void> => {
    await setKeyword(keyword);
    await setPageNumber(0);
    refetch();
  };

  const selectCategory =
    (id: CategoryType['id']) => async (): Promise<void> => {
      await setSelectedCategoryId(id);
      await setPageNumber(0);
      refetch();
    };

  const resetSelectedCategoryId = (): void => {
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
