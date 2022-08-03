import { ReactComponent as ClockSVG } from 'assets/clock.svg';
import { ReactComponent as LocationSVG } from 'assets/location.svg';
import CategorySVG from 'components/svg/Category';
import PersonSVG from 'components/svg/Person';
import { CategoryType, GroupDetailData, GroupParticipants } from 'types/data';
import { parsedDurationDate } from 'utils/date';

import ControlButton from './ControlButton';
import * as S from './index.styled';

// TODO: 달력과 함께 표기

const svgSize = 32;

type InfoProps = Pick<
  GroupDetailData,
  'id' | 'host' | 'duration' | 'finished' | 'location'
> & {
  categoryName: CategoryType['name'];
  participants: GroupParticipants;
};

function Info({
  id,
  host,
  duration,
  categoryName,
  finished,
  location,
  participants,
}: InfoProps) {
  return (
    <S.Container>
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
