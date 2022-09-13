import * as S from './index.styled';
import Info from './Info';
import Withdrawal from './Withdrawal';

function MyInformation() {
  return (
    <S.Container>
      <S.InfoSection>
        <S.InfoContainer>
          <h2>내 정보</h2>
          <Info />
        </S.InfoContainer>
        <Withdrawal />
      </S.InfoSection>
    </S.Container>
  );
}

export default MyInformation;
