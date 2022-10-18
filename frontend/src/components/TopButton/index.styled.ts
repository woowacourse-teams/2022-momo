import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const dungdung = keyframes`
  0%, 100% {
    transform: translate3d(0, 0, 0);
  }

  50% {
    transform: translate3d(0, -0.5rem, 0);
  }
`;

const Button = styled.button`
  position: fixed;
  right: 16px;
  bottom: 16px;

  width: fit-content;
  aspect-ratio: 1 / 1;

  border-radius: 50%;

  padding: 8px;

  animation: 1s ${dungdung} infinite;

  &:focus {
    outline: none;
  }

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    background: ${colors.white001};
    filter: drop-shadow(0 0 2px ${colors.gray001});

    @media only screen and (max-width: ${md}px) {
      bottom: 72px;
    }
  `}
`;

export { Button };
