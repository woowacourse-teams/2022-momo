import Participants from 'pages/Detail/Participants';
import Schedule from 'pages/Detail/Schedule';
import { GroupDetailData, GroupParticipants } from 'types/data';

import * as S from './index.styled';

interface RightProps
  extends Pick<
    GroupDetailData,
    'host' | 'capacity' | 'duration' | 'schedules'
  > {
  participants: GroupParticipants;
}

function Right({
  host,
  capacity,
  duration,
  schedules,
  participants,
}: RightProps) {
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

export default Right;
