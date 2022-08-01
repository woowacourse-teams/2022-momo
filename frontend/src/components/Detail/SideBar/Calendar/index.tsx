import CalendarComponent from 'components/@shared/Calendar';
import useDate from 'hooks/useDate';

import * as S from './index.styled';

function Calendar() {
  const { today, year, month, goToPrevMonth, goToNextMonth } = useDate();

  return (
    <S.Container>
      <CalendarComponent
        year={year}
        month={month}
        goToPrevMonth={goToPrevMonth}
        goToNextMonth={goToNextMonth}
        today={today}
        size="medium"
      />
    </S.Container>
  );
}

export default Calendar;
