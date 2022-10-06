import styled from '@emotion/styled';

const CategoryContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.75rem;

  background: ${({ theme: { colors } }) => colors.gray003};

  width: 100%;

  padding: 1rem 0;
  margin: auto;
`;

const RefreshButton = styled.button`
  font-size: 1rem;

  padding: 0.6rem 1rem;

  border-radius: 1rem;

  transition: background-color 0.25s;

  ${({ theme: { colors } }) => `
    background: ${colors.green001};
    
    :hover {
      background: ${colors.green002};
    }
  `}
`;

export { CategoryContainer, RefreshButton };
