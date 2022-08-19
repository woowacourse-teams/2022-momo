import styled from '@emotion/styled';

const Box = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1.2rem;

  width: 100%;
  height: 3.5rem;
`;

const Button = styled.button`
  min-width: 5rem;
  height: 2rem;

  border: none;
  border-radius: 20px;

  background: none;
  color: ${({ theme: { colors } }) => colors.black002};

  font-weight: 700;
  font-size: 1.25rem;

  &:hover,
  &.select {
    border: 1px solid ${({ theme: { colors } }) => colors.gray002};

    background: ${({ theme: { colors } }) => colors.gray002}cc;
  }
`;

export { Box, Button };
