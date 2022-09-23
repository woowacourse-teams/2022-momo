import { CrownSVG } from 'assets/svg';
import PersonSVG from 'components/svg/Person';
import { GroupDetailData, GroupParticipants } from 'types/data';

import * as S from './index.styled';

const svgSize = 25;

interface ParticipantsProps extends Pick<GroupDetailData, 'host' | 'capacity'> {
  participants: GroupParticipants;
}

function Participants({ host, capacity, participants }: ParticipantsProps) {
  const participantsWithoutHost = participants.filter(
    participant => participant.id !== host.id,
  );

  return (
    <S.Container>
      <S.Header>참여자 목록</S.Header>
      <S.Summary>
        (<span>{participants?.length}</span>명 / 최대 <span>{capacity}</span>명)
      </S.Summary>
      <S.Box>
        <S.Wrapper>
          <CrownSVG width={svgSize} />
          <S.HostText>{host.name}</S.HostText>
        </S.Wrapper>
        {participantsWithoutHost.map(participant => (
          <S.Wrapper key={participant.id}>
            <PersonSVG width={svgSize} />
            <S.Text>{participant.name}</S.Text>
          </S.Wrapper>
        ))}
      </S.Box>
    </S.Container>
  );
}

export default Participants;
