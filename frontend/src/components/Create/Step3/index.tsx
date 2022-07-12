import { Container, Heading, LabelContainer } from '../@shared/styled';
import * as S from './index.styled';

function Step3() {
  return (
    <Container>
      <Heading>
        <span>언제부터 언제까지</span> 만날건가요?
      </Heading>
      <LabelContainer>
        <p>기간</p>
        <S.InputWrapper>
          <S.Input type="date" />
          ~
          <S.Input type="date" />
        </S.InputWrapper>
      </LabelContainer>
    </Container>
  );
}

export default Step3;
