import { DetailData } from 'types/data';

import * as S from './index.styled';

// TODO: 마감 기한이 현재부터 얼마나 남았는지 계산
const deadlineRemain = (deadline: Date) => '06:30:33';

function DetailContent({
  name,
  deadline,
  description,
}: Pick<DetailData, 'name' | 'deadline' | 'description'>) {
  return (
    <S.Container>
      <S.TitleWrapper>
        <S.Hashtag>#한 잔 #음주 #치킨</S.Hashtag>
        <S.Title>{name}</S.Title>
      </S.TitleWrapper>
      <S.DescriptionContainer>
        <S.Duration>
          ⏳ 모집 마감까지 {deadlineRemain(deadline)} 남았습니다
        </S.Duration>
        <S.Description>{description}</S.Description>
        <S.LocationMap />
      </S.DescriptionContainer>
    </S.Container>
  );
}

export default DetailContent;
