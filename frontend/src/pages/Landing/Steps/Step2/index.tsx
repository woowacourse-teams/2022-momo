import { memo } from 'react';

import DescImage1 from 'assets/landing/two_1.webp';
import DescImage2 from 'assets/landing/two_2.webp';
import DescImage3 from 'assets/landing/two_3.webp';

import {
  Container,
  HeadingContainer,
  Image,
  LeftHeading,
  LeftHeadingWrapper,
  RightHeading,
  RightHeadingWrapper,
} from '../../@shared/index.styled';
import * as S from './index.styled';

const images = [
  {
    imageSrc: DescImage1,
    width: '20rem',
  },
  {
    imageSrc: DescImage2,
    width: '30rem',
  },
  {
    imageSrc: DescImage3,
    width: '35rem',
  },
];

function Step2({ show }: { show: boolean }) {
  return (
    <Container>
      <HeadingContainer>
        <LeftHeadingWrapper>
          <LeftHeading className={show ? 'show' : ''}>
            각 <p>단계</p>별로 따라가다 보면
          </LeftHeading>
        </LeftHeadingWrapper>
        <RightHeadingWrapper>
          <RightHeading className={show ? 'show' : ''}>
            쉽게 모임을 만들 수 있어요.
          </RightHeading>
        </RightHeadingWrapper>
      </HeadingContainer>
      <S.ImageBox>
        {images.map(image => (
          <Image
            src={image.imageSrc}
            width={image.width}
            className={show ? 'show' : ''}
            key={image.imageSrc}
          />
        ))}
      </S.ImageBox>
    </Container>
  );
}

export default memo(Step2);
