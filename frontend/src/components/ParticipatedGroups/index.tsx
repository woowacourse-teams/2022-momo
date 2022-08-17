import { useEffect, useRef, useState } from 'react';

import { useQuery } from 'react-query';

import { getParticipatedGroups } from 'apis/request/group';
import { Loading } from 'components/@shared/Animation';
import Card from 'components/@shared/Card';
import Checkbox from 'components/@shared/Checkbox';
import NoResult from 'components/@shared/NoResult';
import { QUERY_KEY } from 'constants/key';
import useInfiniteScroll from 'hooks/useInfiniteScroll';
import { GroupList } from 'types/data';

import * as S from './index.styled';

function ParticipatedGroups() {
  const [isExcludeFinished, setIsExcludeFinished] = useState(false);

  const [pageNumber, setPageNumber] = useState(0);
  const { isFetching, data, refetch } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    getParticipatedGroups(pageNumber, isExcludeFinished),
    {
      suspense: true,
    },
  );
  const target = useRef<HTMLDivElement>(null);

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

  useInfiniteScroll(target, isFetching, data, refetch, groups);

  const toggleIsExcludeFinished = async () => {
    await setIsExcludeFinished(prevState => !prevState);
    await setPageNumber(0);
    refetch();
  };

  return (
    <S.Container>
      <Checkbox
        description="마감된 모임 제외"
        checked={isExcludeFinished}
        toggleChecked={toggleIsExcludeFinished}
      />
      {groups.length > 0 ? (
        <>
          <S.GroupListBox>
            {groups.map(group => (
              <Card group={group} key={group.id} />
            ))}
          </S.GroupListBox>
        </>
      ) : (
        <NoResult>
          아직 참여하신 모임이 없어요 ・゜・(ノД`)
          <br />
          새로운 모임에 참여해보는 건 어떨까요?
        </NoResult>
      )}
      {isFetching && (
        <S.LoadingWrapper>
          <Loading />
        </S.LoadingWrapper>
      )}
      <div ref={target} />
    </S.Container>
  );
}

export default ParticipatedGroups;
