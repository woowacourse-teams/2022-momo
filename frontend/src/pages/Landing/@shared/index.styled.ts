import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const LeftHeadingAnimation = keyframes`
  from {
    transform: translate3d(-10rem, 0, 0);

    opacity: 0;
  }

  to {
    transform: translate3d(0, 0, 0);

    opacity: 1;
  }
`;

const RightHeadingAnimation = keyframes`
  from {
    transform: translate3d(10rem, 0, 0);

    opacity: 0;
  }

  to {
    transform: translate3d(0, 0, 0);

    opacity: 1;
  }
`;

const ImageAnimation = keyframes`
  0%, 50% {
    transform: translate3d(50rem, 0, 0);

    opacity: 0;
  }

  to {
    transform: translate3d(0, 0, 0);

    opacity: 1;
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 2rem;

  overflow-x: hidden;

  width: 100%;
  height: 100%;
`;

const HeadingContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  place-items: center;
  gap: 1rem;

  width: 100%;
  height: fit-content;
`;

const LeftHeadingWrapper = styled.div`
  display: flex;
  justify-content: flex-end;

  width: 100%;
`;

const RightHeadingWrapper = styled.div`
  display: flex;
  justify-content: flex-start;

  width: 100%;
`;

const Heading = styled.h1`
  font-size: 1.5rem;

  p {
    display: inline;

    color: ${({ theme: { colors } }) => colors.green001};
  }

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 0.8rem;
    }

    @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
      font-size: 1.2rem;
    }

    @media only screen and (min-width: ${breakpoints.lg}px) {
      font-size: 1.6rem;
    }
  `}
`;

const LeftHeading = styled(Heading)`
  &.show {
    animation: ${LeftHeadingAnimation} 1.5s;
  }
`;

const RightHeading = styled(Heading)`
  &.show {
    animation: ${RightHeadingAnimation} 1.5s;
  }

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      margin-top: 2rem;
    }

    @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
      margin-top: 2.8rem;
    }

    @media only screen and (min-width: ${breakpoints.lg}px) {
      margin-top: 3.6rem;
    }
  `}
`;

const Image = styled.img<{ width: string }>`
  width: ${({ width }) => width};

  border: 1px solid ${({ theme: { colors } }) => colors.gray003};
  border-radius: 20px;

  opacity: 0;

  &.show {
    animation: ${ImageAnimation} 1.5s forwards;
  }
`;

export {
  Container,
  HeadingContainer,
  LeftHeadingWrapper,
  RightHeadingWrapper,
  Heading,
  LeftHeading,
  RightHeading,
  Image,
  LeftHeadingAnimation,
  RightHeadingAnimation,
};
