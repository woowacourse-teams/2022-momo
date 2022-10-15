import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  border-radius: 20px;
  padding: 2rem;

  background: ${({ theme: { colors } }) => colors.gray005};

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      flex-direction: column;
    }
  `};
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      align-items: center;
    }
  `};
`;

const Title = styled.h2`
  font-size: 1.5rem;
`;

export { Container, Description, Title };
