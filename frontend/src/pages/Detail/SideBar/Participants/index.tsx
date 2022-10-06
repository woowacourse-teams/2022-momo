import { useEffect, useState } from 'react';

import { ArrowSVG, CrownSVG } from 'assets/svg';
import PersonSVG from 'components/svg/Person';
import { GUIDE_MESSAGE } from 'constants/message';
import { GroupDetailData, GroupParticipants } from 'types/data';

import * as S from './index.styled';

const svgSize = 25;
const unit = 5;

interface ParticipantsProps extends Pick<GroupDetailData, 'host' | 'capacity'> {
  participants: GroupParticipants;
}

function Participants({ host, capacity, participants }: ParticipantsProps) {
  const participantsWithoutHost = participants.slice(1);

  const initParticipants = participantsWithoutHost.slice(0, unit);
  const [showingParticipants, setShowingParticipants] =
    useState<GroupParticipants>([]);

  useEffect(() => {
    setShowingParticipants(initParticipants);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [participants]);

  const showSomeParticipants = () => {
    setShowingParticipants(initParticipants);
  };

  const showAllParticipants = () => {
    setShowingParticipants(participantsWithoutHost);
  };

  return (
    <S.Container>
      <S.HeaderContainer>
        <h2>참여자 목록</h2>
        <S.Summary>
          (<span>{participants.length}</span>명 / 최대 <span>{capacity}</span>
          명)
        </S.Summary>
      </S.HeaderContainer>
      <S.Box>
        <S.Participant>
          <S.SVGWrapper isHost={true}>
            <CrownSVG width={svgSize} />
          </S.SVGWrapper>
          <S.Name>{host.name || GUIDE_MESSAGE.MEMBER.WITHDRAWAL_MEMBER}</S.Name>
        </S.Participant>
        {showingParticipants.map(
          participant =>
            participant.id !== host.id && (
              <S.Participant key={participant.id}>
                <S.SVGWrapper>
                  <PersonSVG width={svgSize} />
                </S.SVGWrapper>
                <S.Name>
                  {participant.name || GUIDE_MESSAGE.MEMBER.WITHDRAWAL_MEMBER}
                </S.Name>
              </S.Participant>
            ),
        )}
      </S.Box>
      {showingParticipants.length > unit ? (
        <S.Button type="button" onClick={showSomeParticipants} reverse={true}>
          <ArrowSVG />
        </S.Button>
      ) : (
        participantsWithoutHost.length > unit && (
          <S.Button type="button" onClick={showAllParticipants}>
            <ArrowSVG />
          </S.Button>
        )
      )}
    </S.Container>
  );
}

export default Participants;
