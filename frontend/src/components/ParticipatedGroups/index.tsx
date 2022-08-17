import { useEffect, useRef, useState } from 'react';

import { useQuery } from 'react-query';

import { getParticipatedGroups } from 'apis/request/group';
import { Loading } from 'components/@shared/Animation';
import Card from 'components/@shared/Card';
import NoResult from 'components/@shared/NoResult';
import { QUERY_KEY } from 'constants/key';
import useInfiniteScroll from 'hooks/useInfiniteScroll';
import { GroupList } from 'types/data';

import * as S from './index.styled';

function ParticipatedGroups() {
  const [pageNumber, setPageNumber] = useState(0);
  const { isFetching, data, refetch } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    getParticipatedGroups(pageNumber),
    {
      suspense: true,
    },
  );
  const target = useRef<HTMLDivElement>(null);

  const [groups, setGroups] = useState<GroupList['groups']>([]);

  useEffect(() => {
    if (!data) return;

    setGroups(prevState => [...prevState, ...data.groups]);

    if (!data.hasNextPage) return;

    setPageNumber(data.pageNumber + 1);
  }, [data]);

  useInfiniteScroll(target, isFetching, data, refetch, groups);

  return (
    <S.Container>
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
