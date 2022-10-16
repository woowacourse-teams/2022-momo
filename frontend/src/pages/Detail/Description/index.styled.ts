import styled from '@emotion/styled';

import { DescriptionBox } from '../@shared/index.styled';

const Container = styled(DescriptionBox)`
  display: flex;
  flex-direction: column;
  justify-content: center;

  line-height: 2rem;
  white-space: pre-wrap;

  color: ${({ theme: { colors } }) => colors.black002};
`;

const DescriptionContainer = styled(Container)`
  justify-content: flex-start;

  max-height: 20rem;

  overflow-y: scroll;
`;

const LocationContainer = styled(Container)`
  gap: 1rem;
`;

export { DescriptionContainer, LocationContainer };
