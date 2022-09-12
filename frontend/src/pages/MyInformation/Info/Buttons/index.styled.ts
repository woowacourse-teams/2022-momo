import styled from '@emotion/styled';

import { FlexBox } from '../index.styled';

const ButtonBox = styled(FlexBox)`
  flex-direction: column;
  justify-content: flex-end;
  gap: 1.5rem;

  margin-bottom: 0.2rem;
`;

const EditButton = styled.button`
  background: none;
`;

export { ButtonBox, EditButton };
