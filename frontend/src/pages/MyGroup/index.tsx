import { useEffect, useState } from 'react';

import { useQuery } from 'react-query';

import { requestJoinedGroups } from 'apis/request/group';
import ErrorBoundary from 'components/ErrorBoundary';
import SearchForm from 'components/SearchForm';
import TopButton from 'components/TopButton';
import { QUERY_KEY } from 'constants/key';
import { GroupList, SelectableGroup } from 'types/data';

import CardList from './CardList';
import * as S from './index.styled';

const groupTypes: { type: SelectableGroup; name: string }[] = [
  { type: 'participated', name: '내가 참여한 모임' },
  { type: 'hosted', name: '내가 주최한 모임' },
  { type: 'liked', name: '내가 찜한 모임' },
];

function MyGroup() {
  const [isExcludeFinished, setIsExcludeFinished] = useState(true);
  const [keyword, setKeyword] = useState('');
  const [pageNumber, setPageNumber] = useState(0);
  const [groups, setGroups] = useState<GroupList['groups']>([]);
  const [selectedGroupType, setSelectedGroupType] =
    useState<SelectableGroup>('participated');

  const { isFetching, data, refetch } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    requestJoinedGroups(
      selectedGroupType,
      pageNumber,
      isExcludeFinished,
      keyword,
    ),
    {
      suspense: true,
    },
  );

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

  return (
    <>
      <S.SearchWrapper>
        <SearchForm search={search} />
      </S.SearchWrapper>
      <S.GroupTypeBox>
        {groupTypes.map(({ type, name }) => (
          <S.Button
            key={type}
            className={selectedGroupType === type ? 'selected' : ''}
            onClick={changeSelectedGroupType(type)}
          >
            {name}
          </S.Button>
        ))}
      </S.GroupTypeBox>
      <S.Content>
        <ErrorBoundary>
          <CardList
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

export default MyGroup;
