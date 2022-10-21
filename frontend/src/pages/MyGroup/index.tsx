import { useEffect, useState } from 'react';

import { useQuery } from 'react-query';

import { requestJoinedGroups } from 'apis/request/group';
import ErrorBoundary from 'components/ErrorBoundary';
import TopButton from 'components/TopButton';
import { QUERY_KEY } from 'constants/key';
import FilterSection from 'pages/Main/FilterSection';
import theme from 'styles/theme';
import { CategoryType, GroupList, SelectableGroup } from 'types/data';

import CardList from './CardList';
import * as S from './index.styled';

interface GroupType {
  type: SelectableGroup;
  name: string;
  shortName: string;
}

const groupTypes: GroupType[] = [
  { type: 'participated', name: '내가 참여한 모임', shortName: '참여' },
  { type: 'hosted', name: '내가 주최한 모임', shortName: '주최' },
  { type: 'liked', name: '내가 찜한 모임', shortName: '찜' },
];

const invalidCategoryId = -1;

function MyGroup() {
  const [isExcludeFinished, setIsExcludeFinished] = useState(true);
  const [keyword, setKeyword] = useState('');
  const [selectedCategoryId, setSelectedCategoryId] =
    useState(invalidCategoryId);

  const [groups, setGroups] = useState<GroupList['groups']>([]);
  const [pageNumber, setPageNumber] = useState(0);
  const [selectedGroupType, setSelectedGroupType] =
    useState<SelectableGroup>('participated');

  const { isFetching, data, refetch } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    requestJoinedGroups(
      selectedGroupType,
      pageNumber,
      isExcludeFinished,
      keyword,
      selectedCategoryId,
    ),
    {
      suspense: true,
    },
  );

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

  const changeSelectedGroupType = (newType: SelectableGroup) => async () => {
    await setSelectedGroupType(newType);
    await setPageNumber(0);
    refetch();
  };

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
      >
        <S.GroupTypeBox>
          {groupTypes.map(({ type, name, shortName }) => (
            <S.Button
              key={type}
              className={selectedGroupType === type ? 'selected' : ''}
              onClick={changeSelectedGroupType(type)}
            >
              <S.Check
                className={selectedGroupType === type ? 'selected' : ''}
              />
              <p>
                {document.body.clientWidth > theme.breakpoints.md
                  ? name
                  : shortName}
              </p>
            </S.Button>
          ))}
        </S.GroupTypeBox>
      </FilterSection>
      <S.Content>
        <ErrorBoundary>
          <CardList
            isFetching={isFetching}
            data={data}
            refetch={refetch}
            groups={groups}
          />
        </ErrorBoundary>
      </S.Content>
      <TopButton />
    </>
  );
}

export default MyGroup;
