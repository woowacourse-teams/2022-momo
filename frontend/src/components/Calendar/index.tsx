import { useTheme } from '@emotion/react';

import LeftArrow from 'components/svg/LeftArrow';
import RightArrow from 'components/svg/RightArrow';
import { GroupDetailData } from 'types/data';

import Content from './Content';
import * as S from './index.styled';

interface CalendarProps {
  year: number;
  month: number;
  goToPrevMonth: () => void;
  goToNextMonth: () => void;
  duration?: GroupDetailData['duration'];
  schedules: GroupDetailData['schedules'];
  selectDate?: (year: number, month: number, date: number) => void;
  selectedDate?: string;
  size: 'medium' | 'large';
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
  size,
}: CalendarProps) {
  const theme = useTheme();

  return (
    <S.Container size={size}>
      <S.Navigator size={size}>
        <S.Arrow onClick={goToPrevMonth}>
          <LeftArrow width={30} color={theme.colors.yellow001} />
        </S.Arrow>
        <div>
          {year}년 {month}월
        </div>
        <S.RightArrow onClick={goToNextMonth}>
          <RightArrow width={30} color={theme.colors.yellow001} />
        </S.RightArrow>
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
