import { useRef } from 'react';

import {
  QueryObserverResult,
  RefetchOptions,
  RefetchQueryFilters,
} from 'react-query';

import { Loading } from 'components/Animation';
import Card from 'components/Card';
import NoResult from 'components/NoResult';
import useInfiniteScroll from 'hooks/useInfiniteScroll';
import { GroupList } from 'types/data';

import * as S from './index.styled';

interface CardListProps {
  isFetching: boolean;
  data: GroupList | undefined;
  refetch: (
    options?: RefetchOptions & RefetchQueryFilters<GroupList>,
  ) => Promise<QueryObserverResult<GroupList, unknown>>;
  groups: GroupList['groups'];
}

function CardList({ isFetching, data, refetch, groups }: CardListProps) {
  const target = useRef<HTMLDivElement>(null);

  useInfiniteScroll({ target, isFetching, data, refetch, groups });

  return (
    <S.Container>
      {groups.length > 0 ? (
        <S.GroupListBox>
          {groups.map(group => (
            <Card group={group} key={group.id} />
          ))}
        </S.GroupListBox>
      ) : (
        <NoResult>
          아직 모임이 없어요 ・゜・(ノД`)
          <br />
          새로운 모임을 찾아보는 건 어떨까요?
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

export default CardList;
