import styled from '@emotion/styled';

import { Input as BasicInput } from '../@shared/styled';

const InputWrapper = styled.div`
  display: flex;
  align-items: baseline;
  gap: 1rem;
`;

const Input = styled(BasicInput)`
  width: 12rem;
  height: 3rem;
`;

export { InputWrapper, Input };
