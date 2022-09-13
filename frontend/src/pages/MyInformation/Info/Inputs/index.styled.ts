import styled from '@emotion/styled';

const InputBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-end;
  gap: 1rem;
`;

const Label = styled.label`
  display: flex;
  align-items: center;
  gap: 1rem;
`;

const Input = styled.input`
  width: 15rem;
  height: 2rem;
`;

export { InputBox, Label, Input };
