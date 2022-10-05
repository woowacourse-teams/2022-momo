import { memo } from 'react';

import { TopArrowSVG } from 'assets/svg';
import useThrottle from 'hooks/useThrottle';

import * as S from './index.styled';

function TopButton() {
  const throttledScrollToTop = useThrottle(() => {
    window.scroll({ top: 0, behavior: 'smooth' });
  }, 800);

  return (
    <S.Button type="button" onClick={throttledScrollToTop}>
      <TopArrowSVG />
    </S.Button>
  );
}

export default memo(TopButton);
