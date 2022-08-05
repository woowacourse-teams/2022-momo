import { useTheme } from '@emotion/react';

import LeftArrow from 'components/svg/LeftArrow';
import RightArrow from 'components/svg/RightArrow';
import useCalendar from 'hooks/useCalendar';
import { GroupDetailData } from 'types/data';

import * as S from './index.styled';
import SelectedDate from './SelectedDate';

const days = ['일', '월', '화', '수', '목', '금', '토'];

const dayClassGenerator = (day: string | number) => {
  switch (day) {
    case '토':
    case 6:
      return 'sat';
    case '일':
    case 0:
      return 'sun';
    default:
      return '';
  }
};

interface CalendarProps {
  year: number;
  month: number;
  goToPrevMonth: () => void;
  goToNextMonth: () => void;
  today: Date;
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
  today,
  duration,
  schedules,
  selectDate,
  selectedDate,
  size,
}: CalendarProps) {
  const { dates, prevDates, nextDates } = useCalendar(year, month);
  const theme = useTheme();

  const isToday = (date: number) =>
    today.getFullYear() === year &&
    today.getMonth() === month - 1 &&
    today.getDate() === date;

  const getSchedule = (date: number) => {
    return schedules.find(
      schedule =>
        schedule.date ===
        `${year}-${month.toString().padStart(2, '0')}-${date
          .toString()
          .padStart(2, '0')}`,
    );
  };

  const isNotInDuration = (date: number) => {
    const thisDate = `${year}-${month.toString().padStart(2, '0')}-${date
      .toString()
      .padStart(2, '0')}`;

    if (!duration) return false;

    return thisDate < duration.start || thisDate > duration.end;
  };

  const isSelectedDate = (date: number) => {
    return (
      selectedDate ===
      `${year}-${month.toString().padStart(2, '0')}-${date
        .toString()
        .padStart(2, '0')}`
    );
  };

  const pickDate = (date: number) => () => {
    if (!selectDate) return;

    selectDate(year, month, date);
  };

  return (
    <S.Container size={size}>
      <S.Navigator size={size}>
        <S.LeftArrow onClick={goToPrevMonth}>
          <LeftArrow width={30} color={theme.colors.yellow001} />
        </S.LeftArrow>
        <div>
          {year}년 {month}월
        </div>
        <S.RightArrow onClick={goToNextMonth}>
          <RightArrow width={30} color={theme.colors.yellow001} />
        </S.RightArrow>
      </S.Navigator>
      <S.Content key={month}>
        {/* 요일 */}
        {days.map(day => (
          <S.DayColor className={dayClassGenerator(day)} key={day}>
            {day}
          </S.DayColor>
        ))}
        {/* 지난달 */}
        {prevDates.map(date => (
          <S.PrevNextDate
            className="disabled"
            onClick={goToPrevMonth}
            key={`${month - 1}-${date}`}
          >
            {date}
          </S.PrevNextDate>
        ))}
        {/* 이번달 */}
        {dates.map(date => {
          const schedule = getSchedule(date);

          return schedule ? (
            <SelectedDate
              date={date}
              schedule={schedule}
              key={`${month}-${date}`}
            />
          ) : (
            <S.Date
              className={`
                ${
                  isNotInDuration(date)
                    ? 'disabled'
                    : isSelectedDate(date)
                    ? 'selected'
                    : dayClassGenerator(
                        new Date(year, month - 1, date).getDay(),
                      )
                } ${isToday(date) ? 'today' : ''}`}
              onClick={pickDate(date)}
              key={`${month}-${date}`}
            >
              {date}
            </S.Date>
          );
        })}
        {/* 다음달 */}
        {nextDates.map(date => (
          <S.PrevNextDate
            className="disabled"
            onClick={goToNextMonth}
            key={`${month + 1}-${date}`}
          >
            {date}
          </S.PrevNextDate>
        ))}
      </S.Content>
    </S.Container>
  );
}

export default Calendar;
