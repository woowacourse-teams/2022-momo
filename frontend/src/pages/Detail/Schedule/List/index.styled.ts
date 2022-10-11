import styled from '@emotion/styled';

const ListContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  font-size: 1rem;
`;

const Button = styled.button<{ reverse?: boolean }>`
  background: none;

  transform: ${({ reverse = false }) => (reverse ? 'rotate(180deg)' : '')};
`;

export { ListContainer, Button };
