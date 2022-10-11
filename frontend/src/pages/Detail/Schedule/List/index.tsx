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
        <div key={idx}>· {parsedSchedule(schedule)}</div>
      ))}
      {showingSchedules.length > cutLine ? (
        <S.Button type="button" onClick={showSomeSchedules} reverse={true}>
          <ArrowSVG />
        </S.Button>
      ) : (
        schedules.length > cutLine && (
          <S.Button type="button" onClick={showAllSchedules}>
            <ArrowSVG />
          </S.Button>
        )
      )}
    </S.ListContainer>
  );
}

export default List;
