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

  font-weight: 700;
  font-size: 1.25rem;

  ${({ theme: { colors } }) => `
    color: ${colors.black002};

    &:hover,
    &.select {
      border: 1px solid ${colors.gray002};

      background: ${colors.gray002}cc;
    }
  `}
`;

export { Box, Button };
