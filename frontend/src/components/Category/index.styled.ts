import styled from '@emotion/styled';

const Box = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1.2rem;

  width: 100%;
  height: 3.5rem;

  background: ${({ theme: { colors } }) => colors.gray004};
`;

const Button = styled.button`
  width: 5rem;
  height: 2rem;

  border: none;
  border-radius: 5px;

  background: ${({ theme: { colors } }) => colors.white001};
  color: ${({ theme: { colors } }) => colors.black002};

  font-weight: 700;
  font-size: 1.25rem;
`;

export { Box, Button };
