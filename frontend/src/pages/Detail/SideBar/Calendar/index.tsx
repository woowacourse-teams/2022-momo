import CalendarComponent from 'components/@shared/Calendar';
import useDate from 'hooks/useDate';
import { GroupDetailData } from 'types/data';

import * as S from './index.styled';

interface CalendarProps {
  schedules: GroupDetailData['schedules'];
}

function Calendar({ schedules }: CalendarProps) {
  const { today, year, month, goToPrevMonth, goToNextMonth } = useDate();

  return (
    <S.Container>
      <CalendarComponent
        year={year}
        month={month}
        goToPrevMonth={goToPrevMonth}
        goToNextMonth={goToNextMonth}
        today={today}
        schedules={schedules}
        size="medium"
      />
    </S.Container>
  );
}

export default Calendar;
