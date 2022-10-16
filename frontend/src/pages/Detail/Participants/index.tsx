import { useEffect, useState } from 'react';

import { ArrowSVG, CrownSVG } from 'assets/svg';
import PersonSVG from 'components/svg/Person';
import { GUIDE_MESSAGE } from 'constants/message';
import { GroupDetailData, GroupParticipants } from 'types/data';

import * as S from './index.styled';

const svgSize = 20;
const cutLine = 5;

interface ParticipantsProps extends Pick<GroupDetailData, 'host' | 'capacity'> {
  participants: GroupParticipants;
}

function Participants({ host, capacity, participants }: ParticipantsProps) {
  const participantsWithoutHost = participants.slice(1);

  const previewingParticipants = participantsWithoutHost.slice(0, cutLine);
  const [showingParticipants, setShowingParticipants] =
    useState<GroupParticipants>([]);

  useEffect(() => {
    setShowingParticipants(previewingParticipants);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [participants]);

  const showSomeParticipants = () => {
    setShowingParticipants(previewingParticipants);
  };

  const showAllParticipants = () => {
    setShowingParticipants(participantsWithoutHost);
  };

  return (
    <S.Container>
      <S.HeaderContainer>
        <h2>참여자 목록</h2>
        <S.Summary>
          (<span>{participants.length}</span>명 / <span>{capacity}</span>
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
      {participantsWithoutHost.length > cutLine && (
        <S.Button
          type="button"
          onClick={
            showingParticipants.length > cutLine
              ? showSomeParticipants
              : showAllParticipants
          }
          reverse={showingParticipants.length > cutLine}
        >
          <ArrowSVG />
        </S.Button>
      )}
    </S.Container>
  );
}

export default Participants;
