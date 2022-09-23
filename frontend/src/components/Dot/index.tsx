import * as S from './index.styled';

interface DotProps {
  isFocused: boolean;
}

function Dot({ isFocused }: DotProps) {
  return (
    <S.DotWrapper>
      <S.Dot isFocused={isFocused} />
    </S.DotWrapper>
  );
}

export default Dot;
