import { Link } from 'react-router-dom';

import { BROWSER_PATH } from 'constants/path';
import { GroupSummary } from 'types/data';
import { getCategoryImage } from 'utils/category';
import { convertDeadlineToRemainTime } from 'utils/date';

import * as S from './index.styled';

interface CardProps {
  group: GroupSummary;
}

function Card({ group }: CardProps) {
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
              {group.finished
                ? '마감 완료'
                : convertDeadlineToRemainTime(group.deadline)}
            </S.Deadline>
            <S.Capacity>
              <span>{group.numOfParticipant}</span>명 / 최대{' '}
              <span>{group.capacity}</span>명
            </S.Capacity>
          </S.Right>
        </S.Description>
      </S.Container>
    </Link>
  );
}

export default Card;
