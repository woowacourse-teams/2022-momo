import { Container, Heading, Input } from '../@shared/styled';
import * as S from './index.styled';

function Step6() {
  return (
    <Container>
      <Heading>
        <span>어디에서</span> 모일까요?
      </Heading>
      <S.LabelContainer>
        <p>장소</p>
        <Input type="text" placeholder="둘둘치킨 선릉공원점" />
      </S.LabelContainer>
    </Container>
  );
}

export default Step6;
