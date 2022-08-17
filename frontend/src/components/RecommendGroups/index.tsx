import { useEffect, useRef, useState } from 'react';

import { useQuery } from 'react-query';

import { getGroups } from 'apis/request/group';
import { Loading } from 'components/@shared/Animation';
import Card from 'components/@shared/Card';
import Checkbox from 'components/@shared/Checkbox';
import NoResult from 'components/@shared/NoResult';
import { QUERY_KEY } from 'constants/key';
import useInfiniteScroll from 'hooks/useInfiniteScroll';
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

  const [isExcludeFinished, setIsExcludeFinished] = useState(false);

  useEffect(() => {
    if (!data) return;

    setGroups(prevState => [...prevState, ...data.groups]);

    if (!data.hasNextPage) return;

    setPageNumber(data.pageNumber + 1);
  }, [data]);

  useInfiniteScroll(target, isFetching, data, refetch, groups);

  const toggleIsExcludeFinished = () => {
    setIsExcludeFinished(prevState => !prevState);
  };

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
