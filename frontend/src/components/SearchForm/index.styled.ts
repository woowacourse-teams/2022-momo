import styled from '@emotion/styled';

const Form = styled.form`
  display: flex;

  width: 37rem;
  height: 2.5rem;

  filter: drop-shadow(0 0 4px ${({ theme: { colors } }) => colors.gray001});
`;

const Input = styled.input`
  width: 85%;

  border: none;
  border-radius: 10px 0 0 10px;

  padding: 0 0.5rem;

  &:focus {
    border: none;
  }
`;

const Button = styled.button`
  width: 15%;

  border: none;
  border-radius: 0 10px 10px 0;

  font-size: 1rem;
  font-weight: 700;

  ${({ theme: { colors } }) => `
    background: ${colors.yellow001};
    color: ${colors.white001};
  `}
`;

export { Form, Input, Button };
