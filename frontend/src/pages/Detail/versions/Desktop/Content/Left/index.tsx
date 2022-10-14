import Description from 'pages/Detail/Description';
import Location from 'pages/Detail/Location';
import { GroupDetailData } from 'types/data';

import * as S from './index.styled';

function Left({
  location,
  description,
}: Pick<GroupDetailData, 'location' | 'description'>) {
  return (
    <S.Container>
      <Description type="detail">{description}</Description>
      <Location location={location} />
    </S.Container>
  );
}

export default Left;
