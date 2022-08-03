import { ReactComponent as ClockSVG } from 'assets/clock.svg';
import { ReactComponent as LocationSVG } from 'assets/location.svg';
import CategorySVG from 'components/svg/Category';
import PersonSVG from 'components/svg/Person';
import { CategoryType, GroupDetailData } from 'types/data';
import { parsedDurationDate } from 'utils/date';

import ControlButton from './ControlButton';
import * as S from './index.styled';

// TODO: 달력과 함께 표기

const svgSize = 32;

type InfoProps = Pick<
  GroupDetailData,
  'id' | 'host' | 'duration' | 'location'
> & {
  categoryName: CategoryType['name'];
};

function Info({ id, host, duration, categoryName, location }: InfoProps) {
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
      <ControlButton id={id} host={host} />
    </S.Container>
  );
}

export default Info;
