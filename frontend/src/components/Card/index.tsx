import { useQuery } from 'react-query';
import { Link } from 'react-router-dom';

import { getGroupParticipants } from 'apis/request/group';
import { QUERY_KEY } from 'constants/key';
import { BROWSER_PATH } from 'constants/path';
import { Group, GroupParticipants } from 'types/data';
import { getCategoryImage } from 'utils/category';
import { convertDeadlineToRemainTime } from 'utils/date';

import * as S from './index.styled';

interface CardProps {
  group: Group;
}

function Card({ group }: CardProps) {
  // TODO: 현재 시점(7/29)으로는 아직 현재 모인 인원을 가져올 수 없으므로 모임 참여자를 가져오는 API로 대체
  const { data: participants } = useQuery<GroupParticipants>(
    `${QUERY_KEY.GROUP_PARTICIPANTS}/${group.id}`,
    () => getGroupParticipants(group.id),
    { staleTime: Infinity },
  );

  if (!participants) return <></>;

  const numOfParticipant = participants.length + 1;

  return (
    <Link to={`${BROWSER_PATH.DETAIL}/${group.id}`}>
      <S.Container>
        <S.Image src={getCategoryImage(group.categoryId)} alt="category" />
        <S.Description>
          <S.Left>
            <S.Title>{group.name}</S.Title>
            <S.HostName>{group.host.name}</S.HostName>
            {/* <S.HashtagBox>
              {group.hashtags?.map(hashtag => (
                <S.Hashtag key={hashtag}>#{hashtag}</S.Hashtag>
              ))}
            </S.HashtagBox> */}
          </S.Left>
          <S.Right>
            <S.Deadline>
              {convertDeadlineToRemainTime(group.deadline)}
            </S.Deadline>
            <S.Capacity>
              <span>{numOfParticipant}</span>명 / 최대{' '}
              <span>{group.capacity}</span>명
            </S.Capacity>
          </S.Right>
        </S.Description>
      </S.Container>
    </Link>
  );
}

export default Card;
