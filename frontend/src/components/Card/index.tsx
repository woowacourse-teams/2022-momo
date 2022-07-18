import { Link } from 'react-router-dom';

import { BROWSER_PATH } from 'constants/path';
import { Group } from 'types/data';
import { getCategoryImage } from 'utils/category';
import { convertDeadlineToRemainTime } from 'utils/date';

import * as S from './index.styled';

interface CardProps {
  group: Group;
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
          <S.Deadline>{convertDeadlineToRemainTime(group.deadline)}</S.Deadline>
        </S.Description>
      </S.Container>
    </Link>
  );
}

export default Card;
