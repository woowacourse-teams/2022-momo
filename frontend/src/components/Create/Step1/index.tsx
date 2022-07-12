import { Container, Heading, Input, LabelContainer } from '../@shared/styled';

function Step1() {
  return (
    <Container>
      <Heading>
        모임의 <span>이름</span>을 알려주세요.
      </Heading>
      <LabelContainer>
        <p>이름</p>
        <Input type="text" placeholder="정체를 밝혀라 @_@" autoFocus />
      </LabelContainer>
    </Container>
  );
}

export default Step1;
