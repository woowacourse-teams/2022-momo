import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

import { DescriptionBox } from 'pages/Detail/@shared/index.styled';

const headerAnimation = keyframes`
  from {
    transform: translate3d(100%, 0, 0);
  }

  to {
    transform: translate3d(-130%, 0, 0);
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

    line-height: 1.5rem;

    color: ${({ theme: { colors } }) => colors.black002};

    font-size: 0.9rem;

    animation: ${headerAnimation} linear 15s infinite;

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

    ${({
      theme: {
        colors,
        breakpoints: { md },
      },
    }) => `
      @media only screen and (max-width: ${md}px) {
        width: 1rem;
      }

      &:hover {
        background: ${colors.gray005};
        border-radius: 50%;
      }
    `}
  }
`;

export { Container, Header, SVGBox };
