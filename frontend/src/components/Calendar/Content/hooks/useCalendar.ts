import { GroupDetailData } from 'types/data';
import { convertToISOString } from 'utils/date';

interface CalendarProps {
  year: number;
  month: number;
  duration?: GroupDetailData['duration'];
  schedules: GroupDetailData['schedules'];
  selectDate?: (year: number, month: number, date: number) => void;
  selectedDate?: string;
}

const useCalendar = ({
  year,
  month,
  duration,
  schedules,
  selectDate,
  selectedDate,
}: CalendarProps) => {
  // 이번 달
  const lastDate = new Date(year, month, 0).getDate();
  const dates = [...Array(lastDate)].map((_, idx) => idx + 1);

  // 지난 달
  const prevMonth = new Date(year, month - 1, 0);
  const prevLastDate = prevMonth.getDate();
  const prevDateLengthToShow = (prevMonth.getDay() + 1) % 7;
  const prevDates = [...Array(prevDateLengthToShow)]
    .map((_, idx) => prevLastDate - idx)
    .reverse();

  // 다음 달
  const nextDateLengthToShow = new Date(year, month - 1, lastDate).getDay() + 1;
  const nextDates = [...Array(7 - nextDateLengthToShow)].map(
    (_, idx) => idx + 1,
  );

  const getSchedule = (date: number) => {
    return schedules.find(
      schedule => schedule.date === convertToISOString(year, month, date),
    );
  };

  const isNotInDuration = (date: number) => {
    if (!duration) {
      return false;
    }

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

  return {
    dates,
    prevDates,
    nextDates,
    getSchedule,
    isNotInDuration,
    isSelectedDate,
    pickDate,
  };
};

export default useCalendar;
