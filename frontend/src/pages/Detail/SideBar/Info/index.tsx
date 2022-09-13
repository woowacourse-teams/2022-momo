import { useRecoilValue } from 'recoil';

import { ClockSVG, LocationSVG, PencilSVG } from 'assets/svg';
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
      <S.Content>
        <div>
          <S.Wrapper>
            <ClockSVG width={svgSize} />
            <S.Text>{parsedDurationDate(duration)}</S.Text>
          </S.Wrapper>
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
        </div>
        {user?.id === host.id && (
          <S.Wrapper>
            <PencilSVG width={svgSize - 3} onClick={showGroupEditModal} />
          </S.Wrapper>
        )}
      </S.Content>
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
