import { Spinner } from 'components/@shared/Animation';

import * as S from './index.styled';

function Loading() {
  return (
    <S.Wrapper>
      <Spinner />
    </S.Wrapper>
  );
}

export default Loading;
