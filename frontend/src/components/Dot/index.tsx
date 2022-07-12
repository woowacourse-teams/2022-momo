import * as S from './index.styled';

interface DotProps {
  color: string;
}

function Dot({ color }: DotProps) {
  return (
    <S.DotWrapper>
      <S.Dot color={color} />
    </S.DotWrapper>
  );
}

export default Dot;
