import styled from '@emotion/styled';

const ImageBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;

  img {
    &:first-of-type {
      margin-right: -10rem;
    }

    &:nth-of-type(2) {
      animation-delay: 0.5s;
    }

    &:last-of-type {
      margin-left: -10rem;
      animation-delay: 1s;
    }
  }
`;

export { ImageBox };
