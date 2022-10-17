import styled from '@emotion/styled';

import { preventUserSelect } from 'styles/common';

const Label = styled.label`
  display: flex;
  gap: 0.5rem;

  width: fit-content;

  padding: 0.5rem 0;

  cursor: pointer;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      font-size: 0.9rem;
    }
  `}

  ${preventUserSelect}
`;

const Checkbox = styled.div`
  display: flex;
  justify-content: center;

  width: 1rem;
  height: 1rem;

  border-radius: 4px;

  ${({ theme: { colors } }) => `
    border: 1px solid ${colors.green001};

    &.checked {
      background: ${colors.green001};
    }
  `}
`;

const Check = styled.div`
  transform: rotate3d(0, 0, 1, -45deg);

  width: 0.5rem;
  height: 0.3rem;

  margin-top: 0.2rem;

  ${({ theme: { colors } }) => `
    border-bottom: 2px solid ${colors.white001};
    border-left: 2px solid ${colors.white001};
  `};
`;

export { Label, Checkbox, Check };
