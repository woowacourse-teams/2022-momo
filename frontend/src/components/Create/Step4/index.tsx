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
    setStartDate: (startDate: DurationDate['startDate']) => void;
    setEndDate: (endDate: DurationDate['endDate']) => void;
  };
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

function Step4(
  { useDateState, pressEnterToNext }: Step4Props,
  ref: LegacyRef<HTMLDivElement>,
) {
  const { startDate, setStartDate, endDate, setEndDate } = useDateState();

  const changeStartDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newDate = e.target.value;

    setStartDate(newDate);

    if (!endDate) {
      setEndDate(newDate);
      return;
    }

    if (newDate > endDate) {
      setEndDate(newDate);
    }
  };

  const changeEndDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEndDate(e.target.value);
  };

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
            onChange={changeStartDate}
            min={getNewDateString('day')}
          />
          ~
          <S.Input
            type="date"
            value={endDate}
            onChange={changeEndDate}
            min={startDate || getNewDateString('day')}
            onKeyPress={pressEnterToNext}
          />
        </S.InputWrapper>
      </LabelContainer>
    </Container>
  );
}

export default memo(forwardRef(Step4));
