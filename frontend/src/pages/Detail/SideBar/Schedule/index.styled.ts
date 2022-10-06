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

    min-width: 70%;
  }

  h2 {
    white-space: nowrap;
    word-break: keep-all;

    color: ${({ theme: { colors } }) => colors.black002};

    line-height: 1.5rem;
    font-size: 1rem;

    animation: ${HeaderAnimation} linear 15s infinite;

    ::-webkit-scrollbar {
      display: none;
    }
  }
`;

const SVGBox = styled.div`
  display: flex;
  gap: 0.5rem;

  width: fit-content;

  svg {
    width: 1.5rem;

    cursor: pointer;

    &:hover {
      background: ${({ theme: { colors } }) => colors.gray004};

      border-radius: 50%;
    }
  }
`;

export { Container, Header, SVGBox };
