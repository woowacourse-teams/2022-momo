import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

import { DescriptionBox } from 'pages/Detail/@shared/index.styled';

const HeaderAnimation = keyframes`
  from {
    transform: translateX(100%);
  }

  to {
    transform: translateX(-140%);
  }
`;

const Container = styled(DescriptionBox)`
  width: 100%;

  box-sizing: border-box;

  hr {
    border: 0.5px solid ${({ theme: { colors } }) => colors.gray002};

    margin: 1rem -1rem;
  }
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 2rem;

  section {
    overflow-x: hidden;

    width: 100%;
  }

  h2 {
    white-space: nowrap;
    word-break: keep-all;

    color: ${({ theme: { colors } }) => colors.black002};

    line-height: 1.5rem;

    animation: ${HeaderAnimation} linear 15s infinite;

    font-size: 0.9rem;

    ::-webkit-scrollbar {
      display: none;
    }
  }
`;

const SVGBox = styled.div`
  width: fit-content;

  svg {
    width: 1.5rem;

    cursor: pointer;

    &:hover {
      background: ${({ theme: { colors } }) => colors.gray004};

      border-radius: 50%;
    }

    ${({
      theme: {
        breakpoints: { md },
      },
    }) => `
      @media only screen and (max-width: ${md}px) {
        width: 1rem;
      }
    `}
  }
`;

export { Container, Header, SVGBox };
