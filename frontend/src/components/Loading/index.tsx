import { Spinner } from 'components/Animation';

import * as S from './index.styled';

function Loading(): JSX.Element {
  return (
    <S.Wrapper>
      <Spinner />
    </S.Wrapper>
  );
}

export default Loading;
