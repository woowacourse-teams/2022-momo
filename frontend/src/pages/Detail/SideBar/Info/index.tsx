import { useRecoilValue } from 'recoil';

import { ReactComponent as ClockSVG } from 'assets/svg/clock.svg';
import { ReactComponent as LocationSVG } from 'assets/svg/location.svg';
import { ReactComponent as PencilSVG } from 'assets/svg/pencil.svg';
import CategorySVG from 'components/svg/Category';
import PersonSVG from 'components/svg/Person';
import useModal from 'hooks/useModal';
import { loginState } from 'store/states';
import { CategoryType, GroupDetailData, GroupParticipants } from 'types/data';
import { parsedDurationDate } from 'utils/date';

import ControlButton from './ControlButton';
import * as S from './index.styled';

const svgSize = 25;

interface InfoProps
  extends Pick<
    GroupDetailData,
    'id' | 'host' | 'duration' | 'finished' | 'location'
  > {
  categoryName: CategoryType['name'];
  participants: GroupParticipants;
}

function Info({
  id,
  host,
  duration,
  categoryName,
  finished,
  location,
  participants,
}: InfoProps) {
  const { showGroupEditModal } = useModal();
  const { user } = useRecoilValue(loginState);

  return (
    <S.Container>
      <S.EditWrapper>
        <div>
          <ClockSVG width={svgSize} />
          <S.Text>{parsedDurationDate(duration)}</S.Text>
        </div>
        {user?.id === host.id && (
          <PencilSVG width={svgSize} onClick={showGroupEditModal} />
        )}
      </S.EditWrapper>
      <S.Wrapper>
        <LocationSVG width={svgSize} />
        <S.Text>{location}</S.Text>
      </S.Wrapper>
      <S.Wrapper>
        <CategorySVG width={svgSize} />
        <S.Text>{categoryName}</S.Text>
      </S.Wrapper>
      <S.Wrapper>
        <PersonSVG width={svgSize} />
        <S.Text>{host.name}</S.Text>
      </S.Wrapper>
      <ControlButton
        id={id}
        host={host}
        finished={finished}
        participants={participants}
      />
    </S.Container>
  );
}

export default Info;
