import { GroupDetailData, GroupParticipants } from 'types/data';

import * as S from './index.styled';
import Participants from './Participants';
import Schedule from './Schedule';

interface DetailSideBarProps
  extends Pick<
    GroupDetailData,
    'host' | 'capacity' | 'duration' | 'schedules'
  > {
  participants: GroupParticipants;
}

function DetailSideBar({
  host,
  capacity,
  duration,
  schedules,
  participants,
}: DetailSideBarProps) {
  return (
    <S.Container>
      <Schedule duration={duration} schedules={schedules} />
      <Participants
        host={host}
        capacity={capacity}
        participants={participants}
      />
    </S.Container>
  );
}

export default DetailSideBar;
