import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const openDimmer = keyframes`
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
`;

const openModal = keyframes`
  from {
    transform: scale(0);
  }

  to {
    transform: scale(1);
  }
`;

const closeDimmer = keyframes`
  from {
    opacity: 1;
  }

  to {
    opacity: 0;
  }
`;

const closeModal = keyframes`
  from {
    transform: scale(1);
  }

  to {
    transform: scale(0);
  }
`;

const Dimmer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  left: 0;
  top: 0;

  z-index: 2147483647;

  width: 100%;
  height: 100%;

  background: ${({ theme: { filter } }) => filter.darken001};
  backdrop-filter: saturate(100%) blur(5px);

  ${({ animationTime }: { animationTime: number }) => css`
    animation: ${openDimmer} ${animationTime}ms;

    &.close {
      animation: ${closeDimmer} ${animationTime}ms;
    }
  `}
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  border-radius: 16px;
  padding: 5rem;

  ${({ theme: { colors } }) => css`
    background: ${colors.white001};
    filter: drop-shadow(0 0 4px ${colors.gray001});
  `}

  ${({ animationTime }: { animationTime: number }) => css`
    animation: ${openModal} ${animationTime}ms;

    &.close {
      animation: ${closeModal} ${animationTime}ms;
    }
  `}
`;

export { Dimmer, Content };
