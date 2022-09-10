import CalendarComponent from 'components/Calendar';
import useDate from 'hooks/useDate';
import { CreateGroupData } from 'types/data';

interface CalendarProps {
  duration: {
    start: CreateGroupData['startDate'];
    end: CreateGroupData['endDate'];
  };
  schedules: CreateGroupData['schedules'];
  selectDate: (year: number, month: number, date: number) => void;
  selectedDate: string;
}

function Calendar({
  duration,
  schedules,
  selectDate,
  selectedDate,
}: CalendarProps) {
  const { year, month, goToPrevMonth, goToNextMonth } = useDate();

  return (
    <CalendarComponent
      year={year}
      month={month}
      goToPrevMonth={goToPrevMonth}
      goToNextMonth={goToNextMonth}
      duration={duration}
      schedules={schedules}
      selectDate={selectDate}
      selectedDate={selectedDate}
      size="large"
    />
  );
}

export default Calendar;