import { useState } from 'react';

import { ScheduleType } from 'types/data';
import { parsedTime } from 'utils/date';

import * as S from './index.styled';

interface DateProps {
  date: number;
  schedule: ScheduleType;
}

function SelectedDate({ date, schedule }: DateProps) {
  const [isHovering, setIsHovering] = useState(false);

  const toggleHovering = (isHovering: boolean) => () => {
    setIsHovering(isHovering);
  };

  return (
    <>
      {isHovering && (
        <S.TimeModal>
          ⏰ {parsedTime(schedule?.startTime)} ~ {parsedTime(schedule?.endTime)}{' '}
          ⏰
        </S.TimeModal>
      )}
      <S.SelectedDate
        onMouseOver={toggleHovering(true)}
        onMouseOut={toggleHovering(false)}
      >
        {date}
      </S.SelectedDate>
    </>
  );
}

export default SelectedDate;
