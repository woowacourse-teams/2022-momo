import styled from '@emotion/styled';

import { DescriptionBox } from '../@shared/index.styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const Description = styled(DescriptionBox)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;

  line-height: 2rem;
  white-space: pre-wrap;

  color: ${({ theme: { colors } }) => colors.black002};
`;

const LocationContainer = styled(DescriptionBox)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1rem;
`;

const Location = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;

  margin-left: 0.5rem;
`;

const MapWrapper = styled.div`
  display: flex;
  justify-content: center;

  width: 100%;
`;

const Map = styled.div`
  width: 37rem;
  height: 25rem;

  border-radius: 10px;
`;

export { Container, Description, LocationContainer, Location, MapWrapper, Map };
