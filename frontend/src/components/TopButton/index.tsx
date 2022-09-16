import { memo } from 'react';

import { TopArrowSVG } from 'assets/svg';

import * as S from './index.styled';

function TopButton() {
  let isScrolling = false;

  const scrollToTop = () => {
    if (isScrolling) return;

    isScrolling = true;

    window.scroll({ top: 0, behavior: 'smooth' });

    setTimeout(() => {
      isScrolling = false;
    }, 800);
  };

  return (
    <S.Button type="button" onClick={scrollToTop}>
      <TopArrowSVG />
    </S.Button>
  );
}

export default memo(TopButton);
