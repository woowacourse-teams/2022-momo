import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Heading = styled.h2`
  padding-left: 1rem;

  font-size: 1.5rem;
  font-weight: 300;
`;

const GroupListBox = styled.div`
  display: grid;
  place-items: center;
  row-gap: 3rem;

  width: 100%;

  ${({ theme: { breakpoints } }) => css`
    @media only screen and (max-width: ${breakpoints.md}px) {
      grid-template-columns: repeat(1, 1fr);
    }

    @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
      grid-template-columns: repeat(2, 1fr);
    }

    @media only screen and (min-width: ${breakpoints.lg}px) and (max-width: ${breakpoints.xl}px) {
      grid-template-columns: repeat(3, 1fr);
    }

    @media only screen and (min-width: ${breakpoints.xl}px) {
      grid-template-columns: repeat(4, 1fr);
    }
  `}
`;

export { Heading, GroupListBox };
