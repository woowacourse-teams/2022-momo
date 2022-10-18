import styled from '@emotion/styled';

import { Input as BasicInput } from '../@shared/styled';

const InputWrapper = styled.div`
  display: flex;
  align-items: baseline;
  gap: 1rem;
`;

const Input = styled(BasicInput)`
  cursor: pointer;
`;

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const Label = styled.label`
  display: flex;
  align-items: center;
  gap: 1rem;
`;

const Name = styled.p`
  min-width: fit-content;
`;

export { InputContainer, InputWrapper, Input, Label, Name };
