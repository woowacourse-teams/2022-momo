import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.875rem;

  width: 100%;

  margin: 0 auto;
  padding: 2.5rem 0;

  ${({ theme: { breakpoints } }) => css`
    @media only screen and (max-width: ${breakpoints.md}px) {
      align-items: center;
      max-width: ${breakpoints.sm}px;
    }

    @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
      max-width: ${breakpoints.md}px;
    }

    @media only screen and (min-width: ${breakpoints.lg}px) and (max-width: ${breakpoints.xl}px) {
      max-width: ${breakpoints.lg}px;
    }

    @media only screen and (min-width: ${breakpoints.xl}px) {
      max-width: ${breakpoints.xl}px;
    }
  `}
`;

export { Content };
