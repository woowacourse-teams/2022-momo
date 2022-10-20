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

const Divider = styled.div`
  display: block;

  padding: 0.5rem;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;

  position: relative;

  overflow: hidden;

  width: 100%;
  min-height: 100%;

  padding: 5rem 0;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md - 1}px) {
      flex-direction: column;

      padding-top: 5rem;
    }
  `}
`;

const HeadingContainer = styled.div`
  display: flex;
  gap: 1rem;

  width: 100%;
  height: fit-content;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md - 1}px) {
      flex-direction: column;
    }
  `}
`;

const RightHeadingWrapper = styled.div`
  display: flex;

  width: 100%;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md - 1}px) {
      justify-content: center;
    }
  `}
`;

const LeftHeadingWrapper = styled(RightHeadingWrapper)`
  justify-content: flex-end;
`;

const Heading = styled.h1`
  display: none;

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
    display: block;

    animation: ${LeftHeadingAnimation} 1s;
  }
`;

const RightHeading = styled(Heading)`
  &.show {
    display: block;

    animation: ${RightHeadingAnimation} 1s;
  }

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      margin-top: 0rem;
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
  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      width: 15rem;
    }

    @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
      width: 30rem;
    }

    @media only screen and (min-width: ${breakpoints.lg}px) {
      width: 40rem;
    }
  `}

  width: ${({ width }) => width};

  border: 1px solid ${({ theme: { colors } }) => colors.gray003};
  border-radius: 20px;

  opacity: 0;

  &.show {
    animation: ${ImageAnimation} 1.5s forwards;
  }
`;

export {
  Divider,
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
