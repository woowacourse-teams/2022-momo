import useCalendar from 'hooks/useCalendar';
import { GroupDetailData } from 'types/data';
import { convertToISOString, isToday } from 'utils/date';

import * as S from '../index.styled';
import SelectedDate from '../SelectedDate';

const days = ['일', '월', '화', '수', '목', '금', '토'];

const dayClassNameGenerator = (day: string | number) => {
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

interface ContentProps {
  value: {
    year: number;
    month: number;
    duration?: GroupDetailData['duration'];
    schedules: GroupDetailData['schedules'];
    selectDate?: (year: number, month: number, date: number) => void;
    selectedDate?: string;
    goToPrevMonth: () => void;
    goToNextMonth: () => void;
  };
}

function Content({
  value: {
    year,
    month,
    duration,
    schedules,
    selectDate,
    selectedDate,
    goToPrevMonth,
    goToNextMonth,
  },
}: ContentProps) {
  const { dates, prevDates, nextDates } = useCalendar(year, month);

  const getSchedule = (date: number) => {
    return schedules.find(
      schedule => schedule.date === convertToISOString(year, month, date),
    );
  };

  const isNotInDuration = (date: number) => {
    if (!duration) return false;

    const thisDate = convertToISOString(year, month, date);

    return thisDate < duration.start || thisDate > duration.end;
  };

  const isSelectedDate = (date: number) => {
    return selectedDate === convertToISOString(year, month, date);
  };

  const pickDate = (date: number) => () => {
    if (!selectDate) return;

    selectDate(year, month, date);
  };

  return (
    <S.Content key={month}>
      {/* 요일 */}
      {days.map(day => (
        <S.DayColor className={dayClassNameGenerator(day)} key={day}>
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
            pickDate={pickDate}
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
              : dayClassNameGenerator(new Date(year, month - 1, date).getDay())
          } ${isToday(year, month, date) ? 'today' : ''}`}
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
  );
}

export default Content;
