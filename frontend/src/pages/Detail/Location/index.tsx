import { LocationSVG } from 'assets/svg';
import ErrorBoundary from 'components/ErrorBoundary';
import LocationFallback from 'components/ErrorBoundary/Fallback/Location';
import { GroupDetailData } from 'types/data';
import { processLocation } from 'utils/location';

import Description from '../Description';
import * as S from './index.styled';
import Map from './Map';

const svgSize = 25;

interface LocationProps {
  location: GroupDetailData['location'];
}

function Location({ location }: LocationProps) {
  return (
    <>
      {location.address ? (
        <Description type="location">
          <S.Location>
            <LocationSVG width={svgSize} />
            {processLocation(location)}
          </S.Location>
          <ErrorBoundary fallbackUI={<LocationFallback />}>
            <Map location={location} />
          </ErrorBoundary>
        </Description>
      ) : (
        <Description type="location" />
      )}
    </>
  );
}

export default Location;
