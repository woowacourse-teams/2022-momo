import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const kongKong = keyframes`
  0% {
    transform: translate3d(0, 0, 0);
  }

  20% {
    transform: translate3d(0.2rem, -1rem, 0);
  }

  30% {
    transform: translate3d(0.3rem, -1rem, 0);
  }

  50% {
    transform: translate3d(0.5rem, 0, 0);
  }

  70% {
    transform: translate3d(0.7rem, -1rem, 0);
  }

  80% {
    transform: translate3d(0.8rem, -1rem, 0);
  }

  100% {
    transform: translate3d(1rem, 0, 0);
  }
`;

const Container = styled.div`
  display: flex;
  gap: 3rem;
`;

const LiveBeanBox = styled.div`
  display: flex;
`;

const LiveBean = styled.div`
  position: relative;

  bottom: 0;

  animation: 1.5s ${kongKong} ease-out infinite alternate;

  &.reverse {
    animation: 1.5s ${kongKong} ease-out infinite alternate-reverse;
  }
`;

const Right = styled.div`
  display: flex;
`;

export { Container, LiveBeanBox, LiveBean, Right };
