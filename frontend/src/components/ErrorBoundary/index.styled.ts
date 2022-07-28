import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  padding: 1rem;

  background: ${({ theme: { colors } }) => colors.gray002};
`;

export { Container };
