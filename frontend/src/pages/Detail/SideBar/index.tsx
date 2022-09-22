import { useQuery } from 'react-query';

import { requestGroupParticipants } from 'apis/request/group';
import { QUERY_KEY } from 'constants/key';
import { CategoryType, GroupDetailData, GroupParticipants } from 'types/data';

import Calendar from './Calendar';
import * as S from './index.styled';
import Info from './Info';
import Participants from './Participants';

interface DetailSideBarProps
  extends Omit<
    GroupDetailData,
    'name' | 'categoryId' | 'deadline' | 'description' | 'like'
  > {
  categoryName: CategoryType['name'];
}

function DetailSideBar({
  id,
  host,
  capacity,
  duration,
  schedules,
  finished,
  location,
  categoryName,
}: DetailSideBarProps) {
  const { data: participants } = useQuery<GroupParticipants>(
    `${QUERY_KEY.GROUP_PARTICIPANTS}/${id}`,
    () => requestGroupParticipants(id),
    { staleTime: Infinity },
  );

  if (!participants) return <></>;

  return (
    <S.Container>
      <Info
        id={id}
        host={host}
        duration={duration}
        finished={finished}
        location={location}
        categoryName={categoryName}
        participants={participants}
      />
      <Calendar schedules={schedules} />
      <Participants
        host={host}
        capacity={capacity}
        participants={participants}
      />
    </S.Container>
  );
}

export default DetailSideBar;
