import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const dungdung = keyframes`
  0%, 100% {
    bottom: 1rem;
  }

  50% {
    bottom: 1.2rem;
  }
`;

const Button = styled.button`
  position: fixed;
  right: 1rem;

  width: 3.5rem;
  height: 3.5rem;

  border-radius: 50%;

  animation: 1s ${dungdung} infinite;

  &:focus {
    outline: none;
  }

  ${({ theme: { colors } }) => `
    filter: drop-shadow(0 0 2px ${colors.gray001});

    background: ${colors.white001};
  `}
`;

export { Button };
