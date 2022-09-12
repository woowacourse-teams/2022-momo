import * as S from './index.styled';
import Info from './Info';
import Withdrawal from './Withdrawal';

function MyInformation() {
  return (
    <S.Container>
      <S.InfoSection>
        <S.BoxContents>
          <h2>내 정보</h2>
          <Info />
        </S.BoxContents>
        <Withdrawal />
      </S.InfoSection>
    </S.Container>
  );
}

export default MyInformation;
