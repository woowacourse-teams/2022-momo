import { useState } from 'react';

import { ArrowSVG } from 'assets/svg';
import { GroupDetailData } from 'types/data';
import { parsedSchedule } from 'utils/date';

import * as S from './index.styled';

const unit = 3;

function List({ schedules }: Pick<GroupDetailData, 'schedules'>) {
  const initSchedules = schedules.slice(0, unit);
  const [showingSchedules, setShowingSchedules] = useState(initSchedules);

  const showSomeSchedules = () => {
    setShowingSchedules(initSchedules);
  };

  const showAllSchedules = () => {
    setShowingSchedules(schedules);
  };

  return (
    <S.ListContainer>
      {showingSchedules.map((schedule, idx) => (
        <div key={idx}>· {parsedSchedule(schedule)}</div>
      ))}
      {showingSchedules.length > unit ? (
        <S.Button type="button" onClick={showSomeSchedules} reverse={true}>
          <ArrowSVG />
        </S.Button>
      ) : (
        schedules.length > unit && (
          <S.Button type="button" onClick={showAllSchedules}>
            <ArrowSVG />
          </S.Button>
        )
      )}
    </S.ListContainer>
  );
}

export default List;
