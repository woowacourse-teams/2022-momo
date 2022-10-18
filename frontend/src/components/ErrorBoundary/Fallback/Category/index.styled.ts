import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.75rem;

  width: 100%;

  background: ${({ theme: { colors } }) => colors.gray005};
  border-radius: 8px;

  margin: auto;
  padding: 1rem 0;
`;

const RefreshButton = styled.button`
  width: 5rem;
  height: 2rem;

  border-radius: 8px;

  font-size: 0.9rem;

  transition: background-color 0.25s;

  ${({ theme: { colors } }) => `
    background: ${colors.green001};
    color: ${colors.white001};

    &:hover {
      background: ${colors.green002};
    }
  `}
`;

export { Container, RefreshButton };
