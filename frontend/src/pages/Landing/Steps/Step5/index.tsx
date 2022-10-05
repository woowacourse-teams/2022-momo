import { memo } from 'react';

import { useNavigate } from 'react-router-dom';

import { BeanSVG } from 'assets/svg';
import { BROWSER_PATH } from 'constants/path';

import { Container } from '../../@shared/index.styled';
import * as S from './index.styled';

function Step5({ show }: { show: boolean }) {
  const navigate = useNavigate();

  const goToMainPage = () => {
    navigate(BROWSER_PATH.BASE);
  };

  return (
    <Container>
      <S.IconBox>
        <BeanSVG />
        <BeanSVG />
        <BeanSVG />
      </S.IconBox>
      <S.HeadingWrapper>
        <S.DescriptionHeading className={show ? 'show' : ''}>
          한 눈에 참여하는 모임 서비스
        </S.DescriptionHeading>
        <S.TitleHeading className={show ? 'show' : ''}>
          모두 모여라, 모모
        </S.TitleHeading>
      </S.HeadingWrapper>
      <S.Button
        type="button"
        className={show ? 'show' : ''}
        onClick={goToMainPage}
      >
        시작하기
      </S.Button>
    </Container>
  );
}

export default memo(Step5);
