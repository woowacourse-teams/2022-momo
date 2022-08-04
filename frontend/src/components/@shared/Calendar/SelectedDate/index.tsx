import { useState } from 'react';

import { ScheduleType } from 'types/data';
import { parsedTime } from 'utils/date';

import * as S from './index.styled';

interface DateProps {
  date: number;
  schedule: ScheduleType;
}

function SelectedDate({ date, schedule }: DateProps) {
  const [isHover, setIsHover] = useState(false);

  const changeHoverState = (isHover: boolean) => () => {
    setIsHover(isHover);
  };

  return (
    <>
      {isHover && (
        <S.TimeModal
          onMouseOver={changeHoverState(true)}
          onMouseOut={changeHoverState(false)}
        >
          ⏰ {parsedTime(schedule?.startTime)} ~ {parsedTime(schedule?.endTime)}{' '}
          ⏰
        </S.TimeModal>
      )}
      <S.SelectedDate
        onMouseOver={changeHoverState(true)}
        onMouseOut={changeHoverState(false)}
      >
        {date}
      </S.SelectedDate>
    </>
  );
}

export default SelectedDate;
