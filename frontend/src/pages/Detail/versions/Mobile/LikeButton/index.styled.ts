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
  background: none;

  padding: 0;

  svg {
    animation: 0.5s ease-in-out ${bbyong} 1;
  }

  &:focus {
    outline: none;
  }
`;

export { Button };
