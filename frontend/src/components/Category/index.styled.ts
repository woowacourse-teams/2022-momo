import styled from '@emotion/styled';

const CategoryContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1.2rem;

  width: 100%;
  height: 3.5rem;

  background: ${({ theme: { colors } }) => colors.gray004};
`;

const Category = styled.button`
  width: 5rem;
  height: 2rem;

  background: ${({ theme: { colors } }) => colors.white001};
  color: ${({ theme: { colors } }) => colors.black002};

  border: none;
  border-radius: 5px;

  font-weight: 700;
  font-size: 0.84rem;
`;

export { CategoryContainer, Category };
