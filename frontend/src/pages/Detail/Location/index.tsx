import { LocationSVG } from 'assets/svg';
import useKakaoMap from 'hooks/useKakaoMap';
import { GroupDetailData } from 'types/data';
import { processLocation } from 'utils/location';

import Description from '../Description';
import * as S from './index.styled';

interface LocationProps {
  location: GroupDetailData['location'];
}

function Location({ location }: LocationProps) {
  useKakaoMap(location);

  return (
    <>
      {location.address ? (
        <Description type="location">
          <S.Location>
            <LocationSVG width={25} />
            {processLocation(location)}
          </S.Location>
          <S.MapWrapper>
            <S.Map id="map" />
          </S.MapWrapper>
        </Description>
      ) : (
        <Description type="location" />
      )}
    </>
  );
}

export default Location;
