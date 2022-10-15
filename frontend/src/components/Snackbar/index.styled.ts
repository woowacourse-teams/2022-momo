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

const Container = styled.div<{ animationTime: number }>`
  display: flex;
  justify-content: center;
  align-items: center;

  position: fixed;
  bottom: 2rem;
  left: 50%;

  width: max-content;
  max-width: 40rem;
  height: 4rem;

  border-radius: 4px;
  padding: 0 1rem;

  z-index: 2147483647;

  ${({ theme: { colors } }) => `
    background: ${colors.yellow001}cc;
    color: ${colors.black002};
  `}

  ${({ animationTime }) => css`
    animation: ${show} ${animationTime}ms forwards;

    &.close {
      animation: ${close} ${animationTime}ms;
    }
  `};
`;

export { Container };
