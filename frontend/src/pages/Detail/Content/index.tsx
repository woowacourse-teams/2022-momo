import { LocationSVG } from 'assets/svg';
import useKakaoMap from 'hooks/useKakaoMap';
import { GroupDetailData } from 'types/data';
import { processLocation } from 'utils/location';

import * as S from './index.styled';

function DetailContent({
  location,
  description,
}: Pick<GroupDetailData, 'location' | 'description'>) {
  useKakaoMap(location);

  return (
    <S.Container>
      <S.Description>{description || '(설명이 없어요.)'}</S.Description>
      {location.address ? (
        <S.LocationContainer>
          <S.Location>
            <LocationSVG width={25} />
            {processLocation(location)}
          </S.Location>
          <S.MapWrapper>
            <S.Map id="map" />
          </S.MapWrapper>
        </S.LocationContainer>
      ) : (
        <S.Description>(정해진 장소가 없어요.)</S.Description>
      )}
    </S.Container>
  );
}

export default DetailContent;
