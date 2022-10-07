import styled from '@emotion/styled';

const Form = styled.form`
  display: flex;
  justify-content: center;
  align-items: flex-end;
  gap: 1rem;

  width: 100%;
  height: 2.5rem;
`;

const Input = styled.input`
  width: 100%;

  background: none;

  border: none;
  border-radius: 0;

  padding-bottom: 0.5rem;
  margin-right: 1rem;

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
