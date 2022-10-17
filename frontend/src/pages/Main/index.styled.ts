import styled from '@emotion/styled';

const Content = styled.div`
  width: 100%;

  margin: 2rem auto;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      padding: 9rem 0 4rem;
    }

    @media only screen and (min-width: ${breakpoints.md}px) {
      padding: 10rem 0 1rem;
    }
  `}
`;

export { Content };
