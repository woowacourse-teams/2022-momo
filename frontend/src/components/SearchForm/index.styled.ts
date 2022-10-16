import styled from '@emotion/styled';

const Form = styled.form`
  display: flex;
  justify-content: center;
  align-items: flex-end;
  gap: 0.5rem;

  width: 100%;
  height: 2.5rem;

  svg {
    min-width: 35px;
  }
`;

const Input = styled.input`
  width: 100%;

  background: none;
  border: none;
  border-radius: 0;

  margin: 0 0.5rem;
  padding-bottom: 0.5rem;

  transition: border 0.3s;

  ${({ theme: { colors } }) => `
    border-bottom: 1px solid ${colors.gray001};

    &:focus {
      border: none;
      border-bottom: 1px solid ${colors.green002};
    }
  `}
`;

export { Form, Input };
