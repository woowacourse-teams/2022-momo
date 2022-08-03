import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { Date } from '../index.styled';

const TimeModal = styled.div`
  position: absolute;

  border-radius: 8px;
  padding: 1rem;

  ${({ theme: { colors } }) => css`
    background: ${colors.white001};
    color: ${colors.gray001};
    filter: drop-shadow(0 0 2px ${colors.gray001});
  `}
`;

const SelectedDate = styled(Date)`
  ${({ theme: { colors } }) => css`
    background: ${colors.green002};
    color: ${colors.white001};
  `}
`;

export { TimeModal, SelectedDate };
