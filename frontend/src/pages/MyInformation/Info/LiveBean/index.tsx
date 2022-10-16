import { memo } from 'react';

import { BeanSVG } from 'assets/svg';

import * as S from './index.styled';

function LiveBean() {
  return (
    <S.Box>
      <S.LiveBean className="reverse">
        <BeanSVG />
      </S.LiveBean>
      <S.LiveBean>
        <BeanSVG />
      </S.LiveBean>
    </S.Box>
  );
}

export default memo(LiveBean);
