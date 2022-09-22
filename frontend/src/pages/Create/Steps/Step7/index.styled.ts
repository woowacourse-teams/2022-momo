import styled from '@emotion/styled';

import { Input as BaseInput } from '../@shared/styled';

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
`;

const Label = styled.label`
  display: flex;
  align-items: center;
  gap: 0.5rem;
`;

const Input = styled(BaseInput)`
  cursor: pointer;
`;

export { InputContainer, Label, Input };
