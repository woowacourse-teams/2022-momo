import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 90%;
  aspect-ratio: 1 / 1;

  background: ${({ theme: { colors } }) => colors.gray005};
  border-radius: 8px;

  margin: auto;
`;

export { Container };
