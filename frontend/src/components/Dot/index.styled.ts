import styled from '@emotion/styled';

const DotWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 12px;
  height: 12px;
`;

const Dot = styled.div<{ isFocused: boolean }>`
  width: 12px;
  height: 12px;

  border-radius: 50%;

  transition: 0.2s;

  ${({ isFocused, theme: { colors } }) => `
    background: ${isFocused ? colors.green002 : colors.gray003};

    transform: scale(${isFocused ? 1.5 : 1});
  `};
`;

export { DotWrapper, Dot };
