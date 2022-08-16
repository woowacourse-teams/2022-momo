import { useQuery } from 'react-query';

import { getJoinedGroups } from 'apis/request/group';
import Card from 'components/@shared/Card';
import NoResult from 'components/@shared/NoResult';
import { QUERY_KEY } from 'constants/key';

import * as S from './index.styled';

function JoinedGroups() {
  const { data: groups } = useQuery(
    QUERY_KEY.JOINED_GROUP_SUMMARIES,
    getJoinedGroups,
    {
      suspense: true,
    },
  );

  if (!groups) return <></>;

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
    </S.Container>
  );
}

export default JoinedGroups;
