import { useRecoilValue } from 'recoil';

import { ClockSVG, LocationSVG, PencilSVG } from 'assets/svg';
import CategorySVG from 'components/svg/Category';
import PersonSVG from 'components/svg/Person';
import { GUIDE_MESSAGE } from 'constants/message';
import useModal from 'hooks/useModal';
import { loginState } from 'store/states';
import { CategoryType, GroupDetailData, GroupParticipants } from 'types/data';
import { parsedDurationDate } from 'utils/date';
import { processLocation } from 'utils/location';

import ControlButton from './ControlButton';
import * as S from './index.styled';

const svgSize = 25;

interface InfoProps
  extends Pick<
    GroupDetailData,
    'id' | 'host' | 'capacity' | 'duration' | 'finished' | 'location'
  > {
  categoryName: CategoryType['name'];
  participants: GroupParticipants;
}

function Info({
  id,
  host,
  capacity,
  duration,
  categoryName,
  finished,
  location,
  participants,
}: InfoProps) {
  const { showGroupEditModal } = useModal();
  const { user } = useRecoilValue(loginState);

  const isHost = user?.id === host.id;

  const preparing = () => {
    alert('모임 장소 수정은 준비 중입니다 😅');
  };

  return (
    <S.Container>
      <S.Content>
        <div>
          <S.Wrapper>
            <ClockSVG width={svgSize} />
            <S.Text>{parsedDurationDate(duration)}</S.Text>
          </S.Wrapper>
          <S.Wrapper>
            <LocationSVG width={svgSize} onClick={preparing} />
            <S.Text title={location.address}>
              {processLocation(location)}
            </S.Text>
          </S.Wrapper>
          <S.Wrapper>
            <CategorySVG width={svgSize} />
            <S.Text>{categoryName}</S.Text>
          </S.Wrapper>
          <S.Wrapper>
            <PersonSVG width={svgSize} />
            <S.Text>
              {host.name ? host.name : GUIDE_MESSAGE.MEMBER.WITHDRAWAL_MEMBER}
            </S.Text>
          </S.Wrapper>
        </div>
        {isHost && (
          <S.Wrapper>
            <PencilSVG width={svgSize - 3} onClick={showGroupEditModal} />
          </S.Wrapper>
        )}
      </S.Content>
      <ControlButton
        id={id}
        host={host}
        capacity={capacity}
        finished={finished}
        participants={participants}
      />
    </S.Container>
  );
}

export default Info;
