import { ReactComponent as CrownSVG } from 'assets/crown.svg';
import PersonSVG from 'components/svg/Person';
import { GroupDetailData, GroupParticipants } from 'types/data';

import * as S from './index.styled';

interface ParticipantsProps {
  id: GroupDetailData['id'];
  hostName: GroupDetailData['host']['name'];
  capacity: GroupDetailData['capacity'];
  participants: GroupParticipants;
}

function Participants({
  id,
  hostName,
  capacity,
  participants,
}: ParticipantsProps) {
  return (
    <S.Container>
      <S.Header>참여자 목록</S.Header>
      <S.Summary>
        (<span>{participants?.length}</span>명 / 최대 <span>{capacity}</span>명)
      </S.Summary>
      <S.Box>
        <S.Wrapper>
          <CrownSVG width={32} />
          <S.HostText>{hostName}</S.HostText>
        </S.Wrapper>
        {participants?.map(participant => (
          <S.Wrapper key={participant.id}>
            <PersonSVG width={32} />
            <S.Text>{participant.name}</S.Text>
          </S.Wrapper>
        ))}
      </S.Box>
    </S.Container>
  );
}

export default Participants;
