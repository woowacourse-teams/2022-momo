import styled from '@emotion/styled';

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const Title = styled.h3`
  text-align: center;
`;

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const Label = styled.label`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const Input = styled.input`
  width: 20rem;
  height: 2rem;

  box-sizing: border-box;
`;

const Button = styled.button`
  height: 3rem;

  border-radius: 5px;

  background: ${({ theme: { colors } }) => colors.blue001};
  color: ${({ theme: { colors } }) => colors.white001};

  font-size: 1.1rem;
`;

export { Form, Title, InputContainer, Label, Input, Button };
