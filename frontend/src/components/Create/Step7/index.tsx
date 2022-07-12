import { Container, Heading } from '../@shared/styled';
import * as S from './index.styled';

function Step7() {
  return (
    <Container>
      <Heading>
        <span>모임에 대한 추가적인 설명</span>이 필요하다면 입력해주세요.
        <p>자세히 설명할수록 인원이 모일 확률이 높아져요!</p>
      </Heading>
      <S.TextArea />
    </Container>
  );
}

export default Step7;
