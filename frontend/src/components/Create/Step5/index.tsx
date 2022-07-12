import { Container, Heading, Input, LabelContainer } from '../@shared/styled';
import * as S from './index.styled';

function Step5() {
  return (
    <Container>
      <Heading>
        <span>언제까지</span> 모집할까요?
      </Heading>
      <LabelContainer>
        <S.Label>
          <p>날짜</p>
          <p>D-14</p>
        </S.Label>
        <Input type="date" />
      </LabelContainer>
    </Container>
  );
}

export default Step5;
