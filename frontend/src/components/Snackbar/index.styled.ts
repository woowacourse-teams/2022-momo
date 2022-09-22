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

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  position: fixed;
  left: 50%;
  bottom: 2rem;
  transform: translate3d(-50%, 0, 0);

  width: fit-content;
  max-width: 40rem;
  height: 4rem;

  border-radius: 4px;
  padding: 0 1rem;

  z-index: 999;

  ${({ theme: { colors } }) => `
    background: ${colors.black001}bb;
    color: ${colors.white001};
    filter: drop-shadow(4px 4px 4px ${colors.gray001});
  `}

  ${({ animationTime }: { animationTime: number }) => css`
    animation: ${show} ${animationTime}ms;

    &.close {
      animation: ${close} ${animationTime}ms;
    }
  `};
`;

export { Container };
