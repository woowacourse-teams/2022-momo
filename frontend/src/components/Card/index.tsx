import { Link } from 'react-router-dom';

import { BROWSER_PATH } from 'constants/path';
import { Group } from 'types/data';

import * as S from './index.styled';

interface CardProps {
  group: Group;
}

function Card({ group }: CardProps) {
  return (
    <Link to={`${BROWSER_PATH.DETAIL}/${group.id}`}>
      <S.Container>
        <S.Image />
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
          <S.Deadline>마감까지 06:30:33</S.Deadline>
        </S.Description>
      </S.Container>
    </Link>
  );
}

export default Card;
