import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  padding: 1rem;

  background: ${({ theme: { colors } }) => colors.gray002};
`;

const Title = styled.h2`
  font-size: 2rem;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

export { Container, Title, Description };
