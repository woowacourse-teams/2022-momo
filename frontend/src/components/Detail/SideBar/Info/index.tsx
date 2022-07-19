import { ReactComponent as ClockSVG } from 'assets/clock.svg';
import { ReactComponent as LocationSVG } from 'assets/location.svg';
import CategorySVG from 'components/svg/Category';
import PersonSVG from 'components/svg/Person';
import { CategoryType, DetailData } from 'types/data';

import * as S from './index.styled';

// TODO: 시간 제외 날짜 파싱, 이후에 달력과 함께 표기
const parsedDate = (schedules: DetailData['schedules']) =>
  '2022년 12월 25일 오후 6 ~ 10시';

function Info({
  name,
  schedules,
  categoryName,
  location,
}: Pick<DetailData, 'name' | 'schedules' | 'location'> & {
  categoryName: CategoryType['name'];
}) {
  // TODO: 모임 참여 로직
  const join = () => {
    alert('모임에 참여하였습니다!');
  };

  return (
    <S.Container>
      <S.Wrapper>
        <ClockSVG width={32} />
        <S.Text>{parsedDate(schedules)}</S.Text>
      </S.Wrapper>
      <S.Wrapper>
        <LocationSVG width={32} />
        <S.Text>{location}</S.Text>
      </S.Wrapper>
      <S.Wrapper>
        <CategorySVG width={32} />
        <S.Text>{categoryName}</S.Text>
      </S.Wrapper>
      <S.Wrapper>
        <PersonSVG width={32} />
        <S.Text>{name}</S.Text>
      </S.Wrapper>
      <S.JoinButton type="button" onClick={join}>
        참여하기
      </S.JoinButton>
    </S.Container>
  );
}

export default Info;
