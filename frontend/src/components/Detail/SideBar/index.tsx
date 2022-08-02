import { CategoryType, GroupDetailData } from 'types/data';

import Calendar from './Calendar';
import * as S from './index.styled';
import Info from './Info';
import Participants from './Participants';

function DetailSideBar({
  id,
  name,
  capacity,
  duration,
  schedules,
  location,
  categoryName,
}: Pick<
  GroupDetailData,
  'id' | 'name' | 'capacity' | 'duration' | 'schedules' | 'location'
> & {
  categoryName: CategoryType['name'];
}) {
  return (
    <S.Container>
      <Info
        id={id}
        name={name}
        duration={duration}
        location={location}
        categoryName={categoryName}
      />
      <Calendar schedules={schedules} />
      <Participants id={id} hostName={name} capacity={capacity} />
    </S.Container>
  );
}

export default DetailSideBar;
