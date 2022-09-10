import map from 'assets/map.jpg';
import { GroupDetailData } from 'types/data';
import { getCategoryImage } from 'utils/category';
import { convertDeadlineToRemainTime } from 'utils/date';

import * as S from './index.styled';

function DetailContent({
  name,
  deadline,
  finished,
  categoryId,
  description,
}: Pick<
  GroupDetailData,
  'name' | 'deadline' | 'finished' | 'categoryId' | 'description'
>) {
  return (
    <S.Container>
      <S.TitleWrapper imgSrc={getCategoryImage(categoryId)}>
        <S.Title>{name}</S.Title>
      </S.TitleWrapper>
      <S.DescriptionContainer>
        <S.Duration>
          ⏳ 모집{' '}
          {finished ? '마감 완료' : convertDeadlineToRemainTime(deadline)}
        </S.Duration>
        {description && <S.Description>{description}</S.Description>}
        <S.LocationMap imgSrc={map} />
      </S.DescriptionContainer>
    </S.Container>
  );
}

export default DetailContent;
