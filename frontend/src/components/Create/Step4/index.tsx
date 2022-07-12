import { Container, Heading } from '../@shared/styled';
import * as S from './index.styled';

function Step4() {
  return (
    <Container>
      <Heading>
        <span>언제</span> 만날건가요?
      </Heading>
      <S.Content>
        <S.Calendar />
        <S.Right>
          <S.DailyButton type="button">매일</S.DailyButton>
          <S.DayBox>
            <span className="sun">일</span>
            <span>월</span>
            <span>화</span>
            <span>수</span>
            <span>목</span>
            <span>금</span>
            <span className="sat">토</span>
          </S.DayBox>
          <S.InputWrapper>
            <S.Input type="time" />
            부터
            <S.Input type="time" />
            까지
          </S.InputWrapper>
          <S.AddButton type="button">달력에 추가하기</S.AddButton>
        </S.Right>
      </S.Content>
    </Container>
  );
}

export default Step4;
