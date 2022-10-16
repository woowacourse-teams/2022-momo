import styled from '@emotion/styled';

const Location = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;

  margin-left: 1rem;
`;

const MapWrapper = styled.div`
  display: flex;
  justify-content: center;

  width: 100%;
`;

const Map = styled.div`
  width: 90%;
  aspect-ratio: 1 / 1;

  border-radius: 10px;
`;

export { Location, MapWrapper, Map };
