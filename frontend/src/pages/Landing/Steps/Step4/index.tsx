import { memo } from 'react';

import DescImage from 'assets/landing/four.webp';

import {
  Container,
  HeadingContainer,
  LeftHeadingWrapper,
  LeftHeading,
  RightHeadingWrapper,
  RightHeading,
  Image,
} from '../../@shared/index.styled';

function Step4({ show }: { show: boolean }) {
  return (
    <Container>
      <HeadingContainer>
        <LeftHeadingWrapper>
          <LeftHeading className={show ? 'show' : ''}>
            내가 참여했던 모임이 궁금하신가요?
          </LeftHeading>
        </LeftHeadingWrapper>
        <RightHeadingWrapper>
          <RightHeading className={show ? 'show' : ''}>
            <p>내 모임</p>을 모아볼 수 있어요.
          </RightHeading>
        </RightHeadingWrapper>
      </HeadingContainer>
      <Image src={DescImage} width="40rem" className={show ? 'show' : ''} />
    </Container>
  );
}

export default memo(Step4);
