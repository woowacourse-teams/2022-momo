import { useTheme } from '@emotion/react';

import LeftArrow from 'components/svg/LeftArrow';
import RightArrow from 'components/svg/RightArrow';
import { GroupDetailData } from 'types/data';

import Content from './Content';
import * as S from './index.styled';

const svgSize = 20;

interface CalendarProps {
  year: number;
  month: number;
  goToPrevMonth: () => void;
  goToNextMonth: () => void;
  duration?: GroupDetailData['duration'];
  schedules: GroupDetailData['schedules'];
  selectDate?: (year: number, month: number, date: number) => void;
  selectedDate?: string;
}

function Calendar({
  year,
  month,
  goToPrevMonth,
  goToNextMonth,
  duration,
  schedules,
  selectDate,
  selectedDate,
}: CalendarProps) {
  const theme = useTheme();

  return (
    <S.Container>
      <S.Navigator>
        <S.Arrow direction="left" onClick={goToPrevMonth}>
          <LeftArrow width={svgSize} color={theme.colors.yellow001} />
        </S.Arrow>
        <p>
          {year}년 {month}월
        </p>
        <S.Arrow direction="right" onClick={goToNextMonth}>
          <RightArrow width={svgSize} color={theme.colors.yellow001} />
        </S.Arrow>
      </S.Navigator>
      <Content
        value={{
          year,
          month,
          duration,
          schedules,
          selectDate,
          selectedDate,
          goToPrevMonth,
          goToNextMonth,
        }}
      />
    </S.Container>
  );
}

export default Calendar;
