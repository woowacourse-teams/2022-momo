import styled from '@emotion/styled';

const Image = styled.div<{ src: string }>`
  position: fixed;
  top: 64px;
  z-index: 100;

  width: 100%;
  height: 5rem;

  background: ${({ theme: { filter }, src }) =>
    `linear-gradient(${filter.darken001}, ${filter.darken001}), url(${src})`};
  background-size: cover;
  background-position: center;
`;

const Category = styled.div`
  color: ${({ theme: { colors } }) => colors.blue001};

  font-weight: 900;

  ${({
    theme: {
      breakpoints: { md },
    },
  }) => `
    @media only screen and (max-width: ${md}px) {
      font-size: 0.8rem;
    }
  `}
`;

const Title = styled.h2`
  overflow-x: scroll;
  white-space: nowrap;

  width: 100%;
  line-height: 2rem;

  ::-webkit-scrollbar {
    display: none;
  }

  ${({
    theme: {
      breakpoints: { md, lg },
    },
  }) => `
    @media only screen and (max-width: ${md}px) {
      font-size: 1.1rem;
    }

    @media only screen and (min-width: ${md + 1}px) and (max-width: ${lg}px) {
      font-size: 1.5rem;
    }

    @media only screen and (min-width: ${lg}px) {
      font-size: 1.7rem;
    }
  `}
`;

const Duration = styled.div`
  align-items: center;

  color: ${({ theme: { colors } }) => colors.red003};

  font-weight: 700;

  ${({
    theme: {
      breakpoints: { md },
    },
  }) => `
    @media only screen and (max-width: ${md}px) {
      font-size: 0.8rem;
    }
  `}
`;

export { Image, Category, Title, Duration };
