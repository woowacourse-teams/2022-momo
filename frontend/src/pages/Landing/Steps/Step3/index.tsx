import { memo } from 'react';

import DescImage from 'assets/landing/three.webp';

import {
  Container,
  HeadingContainer,
  Image,
  LeftHeading,
  LeftHeadingWrapper,
  RightHeading,
  RightHeadingWrapper,
} from '../../@shared/index.styled';

function Step3({ show }: { show: boolean }) {
  return (
    <Container>
      <HeadingContainer>
        <LeftHeadingWrapper>
          <LeftHeading className={show ? 'show' : ''}>
            원하는 모임이 생기셨나요?
          </LeftHeading>
        </LeftHeadingWrapper>
        <RightHeadingWrapper>
          <RightHeading className={show ? 'show' : ''}>
            모임에 <p>참여</p>해보세요!
          </RightHeading>
        </RightHeadingWrapper>
      </HeadingContainer>
      <Image src={DescImage} width="40rem" className={show ? 'show' : ''} />
    </Container>
  );
}

export default memo(Step3);
