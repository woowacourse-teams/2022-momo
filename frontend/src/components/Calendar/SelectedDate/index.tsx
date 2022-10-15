import useHover from 'hooks/useHover';
import { ScheduleType } from 'types/data';
import { parsedTime } from 'utils/date';

import * as S from './index.styled';

interface DateProps {
  date: number;
  schedule: ScheduleType;
  pickDate: (date: number) => () => void;
}

function SelectedDate({ date, schedule, pickDate }: DateProps) {
  const { isHover, changeHoverState } = useHover();

  const showTimeModal = changeHoverState(true);
  const hideTimeModal = changeHoverState(false);

  return (
    <>
      {isHover && (
        <S.TimeModal
          onMouseOver={showTimeModal}
          onMouseOut={hideTimeModal}
          onClick={pickDate(date)}
        >
          {parsedTime(schedule.startTime)} ~ {parsedTime(schedule.endTime)}
        </S.TimeModal>
      )}
      <S.SelectedDate
        onMouseOver={showTimeModal}
        onMouseOut={hideTimeModal}
        onClick={pickDate(date)}
      >
        {date}
      </S.SelectedDate>
    </>
  );
}

export default SelectedDate;
