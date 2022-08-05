import { useTheme } from '@emotion/react';

import * as S from './index.styled';

interface DotProps {
  isFocused: boolean;
}

function Dot({ isFocused }: DotProps) {
  const theme = useTheme();

  return (
    <S.DotWrapper>
      <S.Dot isFocused={isFocused} theme={theme} />
    </S.DotWrapper>
  );
}

export default Dot;
