import { useEffect, useRef, useState } from 'react';

import { useQuery } from 'react-query';

import { getGroups } from 'apis/request/group';
import { NoResult } from 'components/Animation';
import Card from 'components/Card';
import { QUERY_KEY } from 'constants/key';
import { GroupList } from 'types/data';

import * as S from './index.styled';

function RecommendGroups() {
  const [pageNumber, setPageNumber] = useState(0);
  const { isLoading, refetch } = useQuery(
    QUERY_KEY.GROUP_SUMMARIES,
    getGroups(pageNumber),
    {
      suspense: true,
    },
  );

  const [groups, setGroups] = useState<GroupList['groups']>([]);
  const target = useRef<HTMLDivElement>(null);

  useEffect(() => {
    let observer: IntersectionObserver;

    const onIntersection = async (
      [entry]: IntersectionObserverEntry[],
      observer: IntersectionObserver,
    ) => {
      if (entry.isIntersecting && !isLoading) {
        refetch().then(({ data }) => {
          if (!data) return;
          if (
            !data.hasNextPage &&
            data.groups.length === 0 &&
            groups.length > 0 &&
            target.current
          ) {
            observer.unobserve(target.current);
            return;
          }

          setPageNumber(data.pageNumber + 1);
          setGroups(prevState => [...prevState, ...data.groups]);
        });
      }
    };

    if (target) {
      observer = new IntersectionObserver(onIntersection, {
        threshold: 0.5,
      });

      if (!target.current) return;
      observer.observe(target.current);
    }

    return () => observer && observer.disconnect();
  }, [target, isLoading, refetch, groups]);

  return (
    <>
      {groups.length > 0 ? (
        <>
          <S.Heading>이런 모임, 어때요?</S.Heading>
          <S.GroupListBox>
            {groups.map(group => (
              <Card group={group} key={group.id} />
            ))}
            <div ref={target} />
          </S.GroupListBox>
        </>
      ) : (
        <S.NoResultContainer>
          <S.NoResultWrapper>
            <NoResult />
            <S.NoResultDescription>
              찾고 계신 모임이 없어요 ・゜・(ノД`)
              <br />
              새로운 모임을 추가해보는 건 어떨까요?
            </S.NoResultDescription>
          </S.NoResultWrapper>
        </S.NoResultContainer>
      )}
    </>
  );
}

export default RecommendGroups;
