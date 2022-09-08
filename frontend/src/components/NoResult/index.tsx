import { NoResult as NoResultAnimation } from 'components/Animation';

import * as S from './index.styled';

interface NoResultProps {
  children: React.ReactNode;
}

function NoResult({ children }: NoResultProps) {
  return (
    <S.NoResultContainer>
      <NoResultAnimation />
      <S.NoResultDescription>{children}</S.NoResultDescription>
    </S.NoResultContainer>
  );
}

export default NoResult;
