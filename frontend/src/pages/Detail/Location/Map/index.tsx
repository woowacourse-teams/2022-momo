import { GroupDetailData } from 'types/data';

import useKakaoMap from './hooks/useKakaoMap';
import * as S from './index.styled';

interface MapProps {
  location: GroupDetailData['location'];
}

function Map({ location }: MapProps): JSX.Element {
  useKakaoMap(location);

  return (
    <S.MapWrapper>
      <S.Map id="map" />
    </S.MapWrapper>
  );
}

export default Map;
