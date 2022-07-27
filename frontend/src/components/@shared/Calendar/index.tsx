import { useTheme } from '@emotion/react';

import LeftArrow from 'components/svg/LeftArrow';
import RightArrow from 'components/svg/RightArrow';
import useCalendar from 'hooks/useCalendar';

import * as S from './index.styled';

interface CalendarProps {
  year: number;
  month: number;
  goToPrevMonth: () => void;
  goToNextMonth: () => void;
}

const days = ['일', '월', '화', '수', '목', '금', '토'];

function Calendar({
  year,
  month,
  goToPrevMonth,
  goToNextMonth,
}: CalendarProps) {
  const { dates, prevDates, nextDates } = useCalendar(year, month);
  const theme = useTheme();

  return (
    <S.Container>
      <S.Navigator>
        <S.Arrow onClick={goToPrevMonth}>
          <LeftArrow width={30} color={theme.colors.yellow001} />
        </S.Arrow>
        <div>
          {year}년 {month}월
        </div>
        <S.Arrow onClick={goToNextMonth}>
          <RightArrow width={30} color={theme.colors.yellow001} />
        </S.Arrow>
      </S.Navigator>
      <S.Content>
        {/* 요일 */}
        {days.map(day => (
          <S.Day key={day}>{day}</S.Day>
        ))}
        {/* 지난달 */}
        {prevDates.map(date => (
          <S.PrevNextDate key={date}>{date}</S.PrevNextDate>
        ))}
        {/* 이번달 */}
        {dates.map(date => (
          <S.Date key={date}>{date}</S.Date>
        ))}
        {/* 다음달 */}
        {nextDates.map(date => (
          <S.PrevNextDate key={date}>{date}</S.PrevNextDate>
        ))}
      </S.Content>
    </S.Container>
  );
}

export default Calendar;
