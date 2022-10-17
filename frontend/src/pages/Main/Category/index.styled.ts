import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const leftFadeOut = keyframes`
  0% {
    transform: translate3d(0, 0, 0);

    opacity: 1;
  }

  30%, 70% {
    transform: translate3d(-1rem, 0, 0);

    opacity: 0;
  }
`;

const rightFadeOut = keyframes`
  0% {
    transform: translate3d(0, 0, 0);

    opacity: 1;
  }

  30%, 70% {
    transform: translate3d(1rem, 0, 0);

    opacity: 0;
  }
`;

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;

  width: 100%;
`;

const Box = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 24px;

  white-space: nowrap;

  overflow-x: auto;
  overflow-y: hidden;

  width: 96%;

  padding: 0.7rem;

  ::-webkit-scrollbar {
    display: none;
  }

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 0.9rem;
    }
  `}
`;

const FloatingLeftButton = styled.div`
  position: absolute;
  left: 0;

  width: 0;

  margin-bottom: 1.5rem;

  animation: ${leftFadeOut} 5s infinite;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (min-width: ${breakpoints.md}px) {
      display: none;
    }
  `};
`;

const FloatingRightButton = styled.div`
  position: absolute;
  right: 35px;

  width: 0;

  margin-bottom: 1.5rem;

  animation: ${rightFadeOut} 5s infinite;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (min-width: ${breakpoints.md}px) {
      display: none;
    }
  `}
`;

const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;

  cursor: pointer;
`;

const Button = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 2.75rem;
  aspect-ratio: 1 / 1;

  border: none;
  border-radius: 50%;

  font-weight: 700;
  font-size: 1rem;

  background: none;

  transition: box-shadow 0.3s, background-color 0.3s;

  p {
    width: 5rem;

    margin-top: 56px;
  }

  svg {
    margin-left: -0.25rem;
  }

  ${({ theme: { colors } }) => `
    &:hover,
    &.select {
      background: ${colors.yellow002}77;
      -webkit-box-shadow : 0px 0px 10px 5px ${colors.yellow002}77;
      -moz-box-shadow : 0px 0px 10px 5px ${colors.yellow002}77;
    }
  `}
`;

export {
  Container,
  Box,
  FloatingLeftButton,
  FloatingRightButton,
  ButtonContainer,
  Button,
};
