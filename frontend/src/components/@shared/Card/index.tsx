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
  const {
    id,
    name,
    categoryId,
    host,
    deadline,
    finished,
    numOfParticipant,
    capacity,
  } = group;

  return (
    <Link to={`${BROWSER_PATH.DETAIL}/${id}`}>
      <S.Container finished={finished}>
        <S.Image imgSrc={getCategoryImage(categoryId)} />
        <S.Description>
          <S.Left>
            <S.Title>{name}</S.Title>
            <S.HostName>{host.name}</S.HostName>
            {/* <S.HashtagBox>
              {hashtags?.map(hashtag => (
                <S.Hashtag key={hashtag}>#{hashtag}</S.Hashtag>
              ))}
            </S.HashtagBox> */}
          </S.Left>
          <S.Right>
            <S.Deadline>
              {finished ? '마감 완료' : convertDeadlineToRemainTime(deadline)}
            </S.Deadline>
            <S.Capacity>
              <span>{numOfParticipant}</span>명 / 최대 <span>{capacity}</span>명
            </S.Capacity>
          </S.Right>
        </S.Description>
      </S.Container>
    </Link>
  );
}

export default Card;
