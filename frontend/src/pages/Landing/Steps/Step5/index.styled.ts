import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

import {
  Container as BasicContainer,
  Heading,
  LeftHeadingAnimation,
  RightHeadingAnimation,
} from '../../@shared/index.styled';

const BbyongBbyong = keyframes`
  0%, 33% {
    transform: translate3d(0, 0, 0) scale3d(1, 1, 1);
  }

  5%, 20% {
    transform: translate3d(0, 0.5rem, 0) scale3d(0.95, 0.8, 1);
  }

  15% {
    transform: translate3d(0, -2rem, 0);
  }

  28% {
    transform: translate3d(0, -1rem, 0) scale3d(1, 1, 1);
  }
`;

const ButtonAnimation = keyframes`
  from {
    transform: translate3d(0, 5rem, 0);
  }

  to {
    transform: translate3d(0, 0, 0);
  }
`;

const Container = styled(BasicContainer)`
  justify-content: center;

  padding: 0;
`;

const IconBox = styled.div`
  display: flex;

  height: max-content;

  svg {
    height: fit-content;

    animation: ${BbyongBbyong} 4.3s infinite;

    &:nth-of-type(2) {
      animation-delay: 1.5s;
    }

    &:last-of-type {
      animation-delay: 3s;
    }

    ${({ theme: { breakpoints } }) => `
      @media only screen and (max-width: ${breakpoints.md}px) {
        width: 3rem;

        &:first-of-type {
          margin-right: -0.5rem;
        }

        &:last-of-type {
          margin-left: -0.5rem;
        }
      }

      @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
        width: 4rem;

        &:first-of-type {
          margin-right: -0.7rem;
        }

        &:last-of-type {
          margin-left: -0.7rem;
        }
      }

      @media only screen and (min-width: ${breakpoints.lg}px) {
        width: 5rem;

        &:first-of-type {
          margin-right: -1rem;
        }

        &:last-of-type {
          margin-left: -1rem;
        }
      }
    `}
  }
`;

const HeadingContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
`;

const DescriptionHeading = styled(Heading)`
  display: block;

  &.show {
    animation: ${LeftHeadingAnimation} 1s;
  }
`;

const TitleHeading = styled(Heading)`
  display: block;

  color: ${({ theme: { colors } }) => colors.green001};

  &.show {
    animation: ${RightHeadingAnimation} 1s;
  }

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 1.5rem;
    }

    @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
      font-size: 2rem;
    }

    @media only screen and (min-width: ${breakpoints.lg}px) {
      font-size: 2.5rem;
    }
  `}
`;

const Button = styled.button`
  width: fit-content;

  background: ${({ theme: { colors } }) => colors.yellow001};

  border-radius: 10px;
  padding: 1rem 2rem;

  &.show {
    animation: ${ButtonAnimation} 1s;
  }

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 1rem;
    }

    @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
      font-size: 1.5rem;
    }

    @media only screen and (min-width: ${breakpoints.lg}px) {
      font-size: 1.8rem;
    }
  `}
`;

export {
  Container,
  IconBox,
  HeadingContainer,
  DescriptionHeading,
  TitleHeading,
  Button,
};
