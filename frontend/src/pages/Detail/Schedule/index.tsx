import { useState } from 'react';

import { CalendarSVG, ListSVG } from 'assets/svg';
import { GroupDetailData } from 'types/data';
import { parsedDurationDate } from 'utils/date';

import Calendar from './Calendar';
import * as S from './index.styled';
import List from './List';

type SelectedType = 'calendar' | 'list';

function Schedule({
  duration,
  schedules,
}: Pick<GroupDetailData, 'duration' | 'schedules'>) {
  const [selected, setSelected] = useState<SelectedType>('list');

  const changeSelected = (newSelected: SelectedType) => () => {
    setSelected(newSelected);
  };

  return (
    <S.Container>
      <S.Header>
        <section>
          <h2>{parsedDurationDate(duration)}</h2>
        </section>
        <S.SVGBox>
          {selected === 'calendar' ? (
            <ListSVG onClick={changeSelected('list')} />
          ) : (
            <CalendarSVG onClick={changeSelected('calendar')} />
          )}
        </S.SVGBox>
      </S.Header>
      <hr />
      {selected === 'calendar' ? (
        <Calendar schedules={schedules} />
      ) : schedules.length === 0 ? (
        <div>일정이 없어요.</div>
      ) : (
        <List schedules={schedules} />
      )}
    </S.Container>
  );
}

export default Schedule;
