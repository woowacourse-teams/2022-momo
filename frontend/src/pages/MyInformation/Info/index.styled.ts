import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  gap: 3rem;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      flex-direction: column;
    }
  `};
`;

const Right = styled.div`
  display: flex;
`;

export { Container, Right };
