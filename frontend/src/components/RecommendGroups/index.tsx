import { useRef } from 'react';

import {
  QueryObserverResult,
  RefetchOptions,
  RefetchQueryFilters,
} from 'react-query';

import { Loading } from 'components/@shared/Animation';
import Card from 'components/@shared/Card';
import Checkbox from 'components/@shared/Checkbox';
import NoResult from 'components/@shared/NoResult';
import useInfiniteScroll from 'hooks/useInfiniteScroll';
import { GroupList } from 'types/data';

import * as S from './index.styled';

interface RecommendGroupsProps {
  isFetching: boolean;
  data: GroupList | undefined;
  refetch: (
    options?: (RefetchOptions & RefetchQueryFilters<GroupList>) | undefined,
  ) => Promise<QueryObserverResult<GroupList, unknown>>;
  groups: GroupList['groups'];
  isExcludeFinished: boolean;
  toggleIsExcludeFinished: () => void;
}

function RecommendGroups({
  isFetching,
  data,
  refetch,
  groups,
  isExcludeFinished,
  toggleIsExcludeFinished,
}: RecommendGroupsProps) {
  const target = useRef<HTMLDivElement>(null);

  useInfiniteScroll(target, isFetching, data, refetch, groups);

  return (
    <S.Container>
      {groups.length > 0 ? (
        <>
          <S.HeadingContainer>
            <S.Heading>이런 모임, 어때요?</S.Heading>
            <Checkbox
              description="마감된 모임 제외"
              checked={isExcludeFinished}
              toggleChecked={toggleIsExcludeFinished}
            />
          </S.HeadingContainer>
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
