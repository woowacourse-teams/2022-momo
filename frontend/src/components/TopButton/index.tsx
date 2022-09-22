import { memo } from 'react';

import { TopArrowSVG } from 'assets/svg';
import useThrottle from 'hooks/useThrottle';

import * as S from './index.styled';

function TopButton() {
  const { throttle } = useThrottle();

  const scrollToTop = () => {
    throttle(() => window.scroll({ top: 0, behavior: 'smooth' }), 800);
  };

  return (
    <S.Button type="button" onClick={scrollToTop}>
      <TopArrowSVG />
    </S.Button>
  );
}

export default memo(TopButton);
