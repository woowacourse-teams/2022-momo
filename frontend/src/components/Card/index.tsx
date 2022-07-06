import * as S from './index.styled';

interface CardProps {
  group: {
    id: number;
    name: string;
    host: {
      id: number;
      name: string;
    };
    categoryId: number;
    isRegular: boolean;
    hashtags?: string[];
    deadline: Date;
  };
}

function Card({ group }: CardProps) {
  return (
    <S.Container>
      <S.Image />
      <S.Description>
        <S.Left>
          <S.Title>{group.name}</S.Title>
          <S.HostName>{group.host.name}</S.HostName>
          <S.HashtagBox>
            {group.hashtags?.map(hashtag => (
              <S.Hashtag key={hashtag}>#{hashtag}</S.Hashtag>
            ))}
          </S.HashtagBox>
        </S.Left>
        <S.Deadline>마감까지 06:30:33</S.Deadline>
      </S.Description>
    </S.Container>
  );
}

export default Card;
