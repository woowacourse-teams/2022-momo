import { useQuery } from 'react-query';

import { getGroupParticipants } from 'apis/request/group';
import { QUERY_KEY } from 'constants/key';
import { CategoryType, GroupDetailData, GroupParticipants } from 'types/data';

import Calendar from './Calendar';
import * as S from './index.styled';
import Info from './Info';
import Participants from './Participants';

function DetailSideBar({
  id,
  host,
  capacity,
  duration,
  schedules,
  location,
  categoryName,
}: Pick<
  GroupDetailData,
  'id' | 'host' | 'capacity' | 'duration' | 'schedules' | 'location'
> & {
  categoryName: CategoryType['name'];
}) {
  const { data: participants } = useQuery<GroupParticipants>(
    `${QUERY_KEY.GROUP_PARTICIPANTS}/${id}`,
    () => getGroupParticipants(id),
    { staleTime: Infinity },
  );

  if (!participants) return <></>;

  return (
    <S.Container>
      <Info
        id={id}
        host={host}
        duration={duration}
        location={location}
        categoryName={categoryName}
        participants={participants}
      />
      <Calendar schedules={schedules} />
      <Participants
        id={id}
        hostName={host.name}
        capacity={capacity}
        participants={participants}
      />
    </S.Container>
  );
}

export default DetailSideBar;
