import { ReactComponent as TopArrowSVG } from 'assets/svg/top_arrow.svg';

import * as S from './index.styled';

function TopButton() {
  const scrollToTop = () => {
    window.scroll({ top: 0, behavior: 'smooth' });
  };

  return (
    <S.Button type="button" onClick={scrollToTop}>
      <TopArrowSVG />
    </S.Button>
  );
}

export default TopButton;
