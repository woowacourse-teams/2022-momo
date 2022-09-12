import styled from '@emotion/styled';

import { FlexBox } from '../index.styled';

const InputBox = styled(FlexBox)`
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

  box-sizing: border-box;
`;

export { InputBox, Label, Input };
