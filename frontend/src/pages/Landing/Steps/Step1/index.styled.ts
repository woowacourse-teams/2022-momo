import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const IconAnimation = keyframes`
  from {
    transform: translate3d(0, 5rem, 0);

    opacity: 0;
  }

  to {
    transform: translate3d(0, 0, 0);

    opacity: 1;
  }
`;

const IconBox = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  place-items: center;
  row-gap: 2rem;

  &.show {
    animation: ${IconAnimation} 0.5s;
  }

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md - 1}px) {
      gap: 0.75rem 1.5rem;

      & > svg {
        width: 2.75rem;
      }
    }
  `}
`;

export { IconBox };
