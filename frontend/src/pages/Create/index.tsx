import {
  Step1,
  Step2,
  Step3,
  Step4,
  Step5,
  Step6,
  Step7,
} from 'components/Create';
import Dot from 'components/Dot';
import LeftArrow from 'components/svg/LeftArrow';
import RightArrow from 'components/svg/RightArrow';
import theme from 'styles/theme';

import * as S from './index.styled';

function Create() {
  return (
    <S.PageContainer>
      <S.ScrollContainer>
        <Step1 />
        <Step2 />
        <Step3 />
        <Step4 />
        <Step5 />
        <Step6 />
        <Step7 />
      </S.ScrollContainer>
      <S.Navigator>
        <button type="button">
          <LeftArrow width={40} color={theme.colors.gray003} />
        </button>
        <button type="button">
          <Dot color={theme.colors.green001} />
        </button>
        <button type="button">
          <Dot color={theme.colors.gray003} />
        </button>
        <button type="button">
          <Dot color={theme.colors.gray003} />
        </button>
        <button type="button">
          <Dot color={theme.colors.gray003} />
        </button>
        <button type="button">
          <Dot color={theme.colors.gray003} />
        </button>
        <button type="button">
          <Dot color={theme.colors.gray003} />
        </button>
        <button type="button">
          <Dot color={theme.colors.gray003} />
        </button>
        <button type="button">
          <RightArrow width={40} color={theme.colors.green001} />
        </button>
      </S.Navigator>
    </S.PageContainer>
  );
}

export default Create;
