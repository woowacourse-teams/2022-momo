import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const bbyong = keyframes`
  0%, 100% {
    transform: scale3d(1, 1, 1);
  }

  50% {
    transform: scale3d(1.2, 1.2, 1.2);
  }
`;

const Button = styled.button`
  position: fixed;
  right: 1rem;
  bottom: 1rem;

  width: 56px;
  height: 56px;

  border-radius: 50%;

  z-index: 998;

  svg {
    animation: 0.5s ease-in-out ${bbyong} 1;
  }

  &:focus {
    outline: none;
  }

  ${({ theme: { colors } }) => `
    filter: drop-shadow(0 0 2px ${colors.gray001});

    background: ${colors.white001};
  `}
`;

export { Button };
