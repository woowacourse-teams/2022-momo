import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  justify-content: flex-end;

  width: 100%;
`;

const Button = styled.button`
  background: none;
  color: ${({ theme: { colors } }) => colors.gray002};
  border-radius: 8px;
`;

export { Wrapper, Button };
