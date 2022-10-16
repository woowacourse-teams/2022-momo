import { useState } from 'react';

import { ArrowSVG } from 'assets/svg';
import { GroupDetailData } from 'types/data';
import { parsedSchedule } from 'utils/date';

import * as S from './index.styled';

const cutLine = 3;

function List({ schedules }: Pick<GroupDetailData, 'schedules'>) {
  const previewingSchedules = schedules.slice(0, cutLine);
  const [showingSchedules, setShowingSchedules] = useState(previewingSchedules);

  const showSomeSchedules = () => {
    setShowingSchedules(previewingSchedules);
  };

  const showAllSchedules = () => {
    setShowingSchedules(schedules);
  };

  return (
    <S.ListContainer>
      {showingSchedules.map((schedule, idx) => (
        <li key={idx}>· {parsedSchedule(schedule)}</li>
      ))}
      {schedules.length > cutLine && (
        <S.Button
          type="button"
          onClick={
            showingSchedules.length > cutLine
              ? showSomeSchedules
              : showAllSchedules
          }
          reverse={showingSchedules.length > cutLine}
        >
          <ArrowSVG />
        </S.Button>
      )}
    </S.ListContainer>
  );
}

export default List;
