import { memo, LegacyRef, forwardRef } from 'react';

import { DurationDate } from 'types/data';
import { getNewDateString } from 'utils/date';

import {
  Container,
  ErrorColor,
  Heading,
  LabelContainer,
} from '../@shared/styled';
import * as S from './index.styled';

interface Step4Props {
  useDateState: () => DurationDate & {
    setStartDate: (e: React.ChangeEvent<HTMLInputElement>) => void;
    setEndDate: (e: React.ChangeEvent<HTMLInputElement>) => void;
  };
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step4(
  { useDateState, pressEnterToNext }: Step4Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { startDate, setStartDate, endDate, setEndDate } = useDateState();

  return (
    <Container ref={ref}>
      <Heading>
        <span>언제부터 언제까지</span> 만날건가요? <ErrorColor>*</ErrorColor>
      </Heading>
      <LabelContainer>
        <p>기간</p>
        <S.InputWrapper>
          <S.Input
            type="date"
            value={startDate}
            onChange={setStartDate}
            min={getNewDateString('day')}
          />
          ~
          <S.Input
            type="date"
            value={endDate}
            onChange={setEndDate}
            min={startDate || getNewDateString('day')}
            onKeyPress={pressEnterToNext}
          />
        </S.InputWrapper>
      </LabelContainer>
    </Container>
  );
}

export default memo(forwardRef(Step4));
