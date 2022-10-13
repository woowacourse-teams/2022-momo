import Participants from 'pages/Detail/Participants';
import Schedule from 'pages/Detail/Schedule';
import { GroupDetailData, GroupParticipants } from 'types/data';

import * as S from './index.styled';

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
