import { memo } from 'react';

import { TopArrowSVG } from 'assets/svg';
import useThrottle from 'hooks/useThrottle';
import theme from 'styles/theme';

import * as S from './index.styled';

function TopButton() {
  const svgSize = document.body.clientWidth > theme.breakpoints.md ? 30 : 25;

  const throttledScrollToTop = useThrottle(() => {
    window.scroll({ top: 0, behavior: 'smooth' });
  }, 800);

  return (
    <S.Button type="button" onClick={throttledScrollToTop}>
      <TopArrowSVG width={svgSize} height={svgSize} />
    </S.Button>
  );
}

export default memo(TopButton);
