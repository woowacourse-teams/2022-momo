import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

import { oneLineEllipsis } from 'styles/common';

const boxAnimation = keyframes`
  from {
    transform: scale3d(1, 0.5, 1);
  }

  to {
    transform: scale3d(1, 1, 1);
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;

  min-width: max-content;

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 10px;

  padding: 1.5rem;
`;

const HeaderContainer = styled.div`
  display: flex;
  justify-content: space-between;

  h2 {
    color: ${({ theme: { colors } }) => colors.black002};

    font-size: 1rem;
  }
`;

const Summary = styled.div`
  font-size: 1rem;

  ${({ theme: { colors } }) => `
    color: ${colors.gray001};

    span {
      color: ${colors.blue002};
    }
  `}
`;

const Box = styled.ul`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  row-gap: 1rem;
  column-gap: 0.5rem;

  transform-origin: top;
  animation: ${boxAnimation} 0.2s ease-in-out;

  ${({
    theme: {
      breakpoints: { lg },
    },
  }) => `
    @media only screen and (max-width: ${lg}px) {
      grid-template-columns: 1fr;
    }
  `}
`;

const Participant = styled.li`
  display: flex;
  align-items: center;
  gap: 0.5rem;

  width: fit-content;
`;

const SVGWrapper = styled.div<{ isHost?: boolean }>`
  width: fit-content;
  height: fit-content;

  background: ${({ theme: { colors }, isHost = false }) =>
    isHost ? colors.yellow001 : colors.gray005};

  border-radius: 50%;
  padding: 0.3rem;
`;

const Name = styled.div`
  max-width: 5rem;

  font-size: 0.9rem;

  ${({
    theme: {
      breakpoints: { lg },
    },
  }) => `
    @media only screen and (max-width: ${lg}px) {
      max-width: 10rem;
    }
  `}

  ${oneLineEllipsis}
`;

const Button = styled.button<{ reverse?: boolean }>`
  background: none;

  transform: ${({ reverse = false }) =>
    reverse ? 'rotate3d(0, 0, 1, 180deg)' : ''};
  transition: transform 0.5s;
`;

export {
  Container,
  HeaderContainer,
  Summary,
  Box,
  Participant,
  SVGWrapper,
  Name,
  Button,
};
