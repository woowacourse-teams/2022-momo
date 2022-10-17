import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const show = keyframes`
  from {
    transform: translate3d(-50%, 2rem, 0);

    opacity: 0;
  }

  to {
    transform: translate3d(-50%, 0, 0);

    opacity: 1;
  }
`;

const close = keyframes`
  from {
    transform: translate3d(-50%, 0, 0);

    opacity: 1;
  }

  to {
    transform: translate3d(-50%, 2rem, 0);

    opacity: 0;
  }
`;

const Container = styled.div<{ animationTime: number; isError: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;

  position: fixed;
  bottom: 2rem;
  left: 50%;

  width: 80%;
  max-width: 30rem;
  height: 4rem;

  border-radius: 4px;
  padding: 0 1rem;

  z-index: 2147483647;

  ${({ theme: { colors }, isError }) => `
    background: ${isError ? colors.red002 : colors.yellow001}cc;
    color: ${isError ? colors.white001 : colors.black002};
  `}

  ${({ animationTime }) => css`
    animation: ${show} ${animationTime}ms forwards;

    &.close {
      animation: ${close} ${animationTime}ms;
    }
  `};
`;

export { Container };
