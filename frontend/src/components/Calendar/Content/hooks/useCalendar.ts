import { GroupDetailData, ScheduleType } from 'types/data';
import { convertToYYYYMMDD } from 'utils/date';

interface CalendarProps {
  year: number;
  month: number;
  duration?: GroupDetailData['duration'];
  schedules: GroupDetailData['schedules'];
  selectDate?: (year: number, month: number, date: number) => void;
  selectedDate?: string;
}

interface UseCalendarReturnType {
  dates: number[];
  prevDates: number[];
  nextDates: number[];
  getSchedule: (date: number) => ScheduleType | undefined;
  isNotInDuration: (date: number) => boolean;
  isSelectedDate: (date: number) => boolean;
  pickDate: (date: number) => () => void;
}

const useCalendar = ({
  year,
  month,
  duration,
  schedules,
  selectDate,
  selectedDate,
}: CalendarProps): UseCalendarReturnType => {
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
      schedule => schedule.date === convertToYYYYMMDD(year, month, date),
    );
  };

  const isNotInDuration = (date: number) => {
    if (!duration) {
      return false;
    }

    const thisDate = convertToYYYYMMDD(year, month, date);

    return thisDate < duration.start || thisDate > duration.end;
  };

  const isSelectedDate = (date: number) => {
    return selectedDate === convertToYYYYMMDD(year, month, date);
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
