import { useEffect, useState } from 'react';

import { useQuery } from 'react-query';

import { getParticipatedGroups } from 'apis/request/group';
import ErrorBoundary from 'components/@shared/ErrorBoundary';
import JoinedGroups from 'components/JoinedGroups';
import SearchForm from 'components/SearchSection/SearchForm';
import { QUERY_KEY } from 'constants/key';
import useInput from 'hooks/useInput';
import { GroupList } from 'types/data';

import * as S from './index.styled';

const groupTypes = [
  { name: '내가 참여한 모임' },
  { name: '내가 주최한 모임' },
  { name: '내가 찜한 모임' },
];

function MyGroup() {
  const [isExcludeFinished, setIsExcludeFinished] = useState(false);
  const { value: keyword, setValue: setKeyword } = useInput('');

  const [pageNumber, setPageNumber] = useState(0);
  const { isFetching, data, refetch } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    getParticipatedGroups(pageNumber, isExcludeFinished, keyword),
    {
      suspense: true,
    },
  );

  const [groups, setGroups] = useState<GroupList['groups']>([]);

  const [selectedGroupType, setSelectedGroupType] = useState(0);

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

  const changeSelectedGroupType = (index: number) => {
    setSelectedGroupType(index);
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
        {groupTypes.map((groupType, i) => (
          <S.Button
            key={groupType.name}
            className={selectedGroupType === i ? 'selected' : ''}
            onClick={() => changeSelectedGroupType(i)}
          >
            {groupType.name}
          </S.Button>
        ))}
      </S.GroupTypeBox>
      <S.Content>
        <ErrorBoundary>
          <JoinedGroups
            isFetching={isFetching}
            data={data}
            refetch={refetch}
            groups={groups}
            isExcludeFinished={isExcludeFinished}
            toggleIsExcludeFinished={toggleIsExcludeFinished}
          />
        </ErrorBoundary>
      </S.Content>
    </>
  );
}

export default MyGroup;
