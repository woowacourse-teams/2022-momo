import { Info, Withdrawal } from 'components/MyInformation';

import * as S from './index.styled';

function MyInformation() {
  return (
    <S.Container>
      <S.Box>
        <S.InfoWrapper>
          <h2>내 정보</h2>
          <Info />
        </S.InfoWrapper>
        <Withdrawal />
      </S.Box>
    </S.Container>
  );
}

export default MyInformation;
