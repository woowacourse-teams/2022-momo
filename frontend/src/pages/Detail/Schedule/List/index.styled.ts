import styled from '@emotion/styled';

const ListContainer = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  font-size: 1rem;
`;

const Button = styled.button<{ reverse?: boolean }>`
  background: none;

  transform: ${({ reverse = false }) => (reverse ? 'rotate(180deg)' : '')};
  transition: transform 0.5s;
`;

export { ListContainer, Button };
