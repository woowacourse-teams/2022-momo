import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

const DotWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 12px;
  height: 12px;
`;

const Dot = styled.div`
  width: 12px;
  height: 12px;

  border-radius: 50%;

  ${({ isFocused, theme }: { isFocused: boolean; theme: Theme }) => css`
    ${isFocused
      ? `
      background: ${theme.colors.green001};
      transform: scale(1.5);
    `
      : `
      background: ${theme.colors.gray003};
      transform: scale(1);
    `}
    transition: 0.2s;
  `};
`;

export { DotWrapper, Dot };
