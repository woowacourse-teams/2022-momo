import CalendarComponent from 'components/Calendar';
import useDate from 'hooks/useDate';
import { GroupDetailData } from 'types/data';

import * as S from './index.styled';

interface CalendarProps {
  schedules: GroupDetailData['schedules'];
}

function Calendar({ schedules }: CalendarProps) {
  const { year, month, goToPrevMonth, goToNextMonth } = useDate();

  return (
    <S.Container>
      <CalendarComponent
        year={year}
        month={month}
        goToPrevMonth={goToPrevMonth}
        goToNextMonth={goToNextMonth}
        schedules={schedules}
      />
    </S.Container>
  );
}

export default Calendar;
