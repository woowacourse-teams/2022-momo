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
  0% {
    transform: scale3d(0.5, 0.5, 0.5);
  }

  100% {
    transform: scale3d(1, 1, 1);
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
    transform: scale3d(1, 1, 1);
  }

  to {
    transform: scale3d(0.5, 0.5, 0.5);
  }
`;

const Dimmer = styled.div<{ animationTime: number }>`
  display: flex;
  justify-content: center;
  align-items: center;

  position: fixed;
  top: 0;

  z-index: 2147483646;

  width: 100%;
  height: 100%;

  background: ${({ theme: { filter } }) => filter.darken001};
  backdrop-filter: saturate(100%) blur(5px);

  ${({ animationTime }) => css`
    animation: ${openDimmer} ${animationTime}ms;

    &.close {
      animation: ${closeDimmer} ${animationTime}ms;
    }
  `}
`;

const Content = styled.div<{ animationTime: number }>`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 95%;
  max-width: 21rem;

  border-radius: 16px;
  padding: 5rem;

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    background: ${colors.white001};
    filter: drop-shadow(0 0 4px ${colors.gray001});

    @media only screen and (max-width: ${md}px) {
      padding: 2rem 0;
    }
  `}

  ${({ animationTime }) => css`
    animation: ${openModal} ${animationTime}ms;

    &.close {
      animation: ${closeModal} ${animationTime}ms;
    }
  `}
`;

export { Dimmer, Content };
