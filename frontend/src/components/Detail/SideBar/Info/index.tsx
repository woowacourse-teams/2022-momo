import { ReactComponent as ClockSVG } from 'assets/clock.svg';
import { ReactComponent as LocationSVG } from 'assets/location.svg';
import CategorySVG from 'components/svg/Category';
import PersonSVG from 'components/svg/Person';
import { DetailData } from 'types/data';

import * as S from './index.styled';

// TODO: 카테고리 가져오기
const category = (categoryId: number) => '식사';

// TODO: 스케줄 기반 날짜 파싱
const parsedDate = (schedules: DetailData['schedules']) =>
  '2022년 7월 8일 오후 6 ~ 10시';

// TODO: 참여 로직
const join = () => {
  alert('모임에 참여하였습니다!');
};

function Info({
  name,
  schedules,
  categoryId,
  location,
}: Pick<DetailData, 'name' | 'schedules' | 'categoryId' | 'location'>) {
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
        <S.Text>{category(categoryId)}</S.Text>
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
