import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  justify-content: flex-end;

  width: 100%;
`;

const Button = styled.button`
  width: 5rem;
  height: 2.5rem;

  border-radius: 8px;

  background: ${({ theme: { colors } }) => colors.red001};
  color: ${({ theme: { colors } }) => colors.white001};
`;

export { Wrapper, Button };
