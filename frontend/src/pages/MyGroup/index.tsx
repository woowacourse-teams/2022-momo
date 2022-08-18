import { useEffect, useState } from 'react';

import { useQuery } from 'react-query';

import { getJoinedGroups } from 'apis/request/group';
import ErrorBoundary from 'components/@shared/ErrorBoundary';
import NoResult from 'components/@shared/NoResult';
import TopButton from 'components/@shared/TopButton';
import JoinedGroups from 'components/JoinedGroups';
import SearchForm from 'components/SearchSection/SearchForm';
import { QUERY_KEY } from 'constants/key';
import useInput from 'hooks/useInput';
import { GroupList, SelectableGroup } from 'types/data';

import * as S from './index.styled';

const groupTypes: { type: SelectableGroup; name: string }[] = [
  { type: 'participated', name: '내가 참여한 모임' },
  { type: 'hosted', name: '내가 주최한 모임' },
  { type: 'liked', name: '내가 찜한 모임' },
];

function MyGroup() {
  const [selectedGroupType, setSelectedGroupType] =
    useState<SelectableGroup>('participated');

  const [isExcludeFinished, setIsExcludeFinished] = useState(false);
  const { value: keyword, setValue: setKeyword } = useInput('');

  const [pageNumber, setPageNumber] = useState(0);
  const { isFetching, data, refetch } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    getJoinedGroups(selectedGroupType, pageNumber, isExcludeFinished, keyword),
    {
      suspense: true,
    },
  );

  const [groups, setGroups] = useState<GroupList['groups']>([]);

  const [isPreparing, setIsPreparing] = useState(false);

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

    // 찜한 목록은 준비 중 페이지로 대체
    if (newType === 'liked') {
      setIsPreparing(true);
      return;
    }

    await setIsPreparing(false);
    await setPageNumber(0);
    refetch();
  };

  const toggleIsExcludeFinished = async () => {
    await setIsExcludeFinished(prevState => !prevState);
    await setPageNumber(0);
    refetch();
  };

  const search = async () => {
    await setPageNumber(0);
    refetch();
  };

  return (
    <>
      <S.SearchWrapper>
        <SearchForm keyword={keyword} setKeyword={setKeyword} search={search} />
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
          {isPreparing ? (
            <NoResult>준비 중이에요 ・゜・(ノД`)</NoResult>
          ) : (
            <JoinedGroups
              isFetching={isFetching}
              data={data}
              refetch={refetch}
              groups={groups}
              isExcludeFinished={isExcludeFinished}
              toggleIsExcludeFinished={toggleIsExcludeFinished}
            />
          )}
        </ErrorBoundary>
      </S.Content>
      <TopButton />
    </>
  );
}

export default MyGroup;
