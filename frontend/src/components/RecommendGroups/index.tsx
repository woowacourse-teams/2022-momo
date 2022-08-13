import { useEffect, useRef, useState } from 'react';

import { useQuery } from 'react-query';

import { getGroups } from 'apis/request/group';
import { Loading } from 'components/Animation';
import Card from 'components/Card';
import NoResult from 'components/NoResult';
import { QUERY_KEY } from 'constants/key';
import { GroupList } from 'types/data';

import * as S from './index.styled';

function RecommendGroups() {
  const [pageNumber, setPageNumber] = useState(0);
  const { isFetching, data, refetch } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    getGroups(pageNumber),
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

  useEffect(() => {
    let observer: IntersectionObserver;

    const onIntersection = async (
      [entry]: IntersectionObserverEntry[],
      observer: IntersectionObserver,
    ) => {
      if (!entry.isIntersecting || isFetching || !data?.hasNextPage) return;

      refetch().then(() => {
        if (!data || !target.current) return;
        if (!data.hasNextPage || groups.length > 0) {
          observer.unobserve(target.current);
          return;
        }
      });
    };

    if (target) {
      observer = new IntersectionObserver(onIntersection, {
        threshold: 0.5,
      });

      if (!target.current) return;
      observer.observe(target.current);
    }

    return () => observer && observer.disconnect();
  }, [isFetching, refetch, groups.length, data]);

  return (
    <S.Container>
      {groups.length > 0 ? (
        <>
          <S.Heading>이런 모임, 어때요?</S.Heading>
          <S.GroupListBox>
            {groups.map(group => (
              <Card group={group} key={group.id} />
            ))}
          </S.GroupListBox>
        </>
      ) : (
        <NoResult>
          찾고 계신 모임이 없어요 ・゜・(ノД`)
          <br />
          새로운 모임을 추가해보는 건 어떨까요?
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

export default RecommendGroups;
