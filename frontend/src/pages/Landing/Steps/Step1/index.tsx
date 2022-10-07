import { memo } from 'react';

import {
  CafeSVG,
  CultureSVG,
  DrinkSVG,
  ExerciseSVG,
  GameSVG,
  GuitarSVG,
  MogackoSVG,
  SicsaSVG,
  StudySVG,
  TravelSVG,
} from 'assets/svg';

import {
  Container,
  HeadingContainer,
  LeftHeading,
  LeftHeadingWrapper,
  RightHeading,
  RightHeadingWrapper,
} from '../../@shared/index.styled';
import * as S from './index.styled';

function Step1({ show }: { show: boolean }) {
  return (
    <Container>
      <HeadingContainer>
        <LeftHeadingWrapper>
          <LeftHeading className={show ? 'show' : ''}>
            다양한 <p>카테고리</p>에서
          </LeftHeading>
        </LeftHeadingWrapper>
        <RightHeadingWrapper>
          <RightHeading className={show ? 'show' : ''}>
            원하는 모임을 찾아보세요.
          </RightHeading>
        </RightHeadingWrapper>
      </HeadingContainer>
      <S.IconBox className={show ? 'show' : ''}>
        <StudySVG />
        <MogackoSVG />
        <SicsaSVG />
        <CafeSVG />
        <DrinkSVG />
        <ExerciseSVG />
        <GameSVG />
        <TravelSVG />
        <CultureSVG />
        <GuitarSVG />
      </S.IconBox>
    </Container>
  );
}

export default memo(Step1);
