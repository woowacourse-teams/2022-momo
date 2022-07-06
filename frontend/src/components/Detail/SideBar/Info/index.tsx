import { ReactComponent as ClockSVG } from 'assets/clock.svg';
import { ReactComponent as LocationSVG } from 'assets/location.svg';
import CategorySVG from 'components/svg/Category';
import PersonSVG from 'components/svg/Person';

import * as S from './index.styled';

function Info() {
  return (
    <S.Container>
      <S.Wrapper>
        <ClockSVG width={32} />
        <S.Text>2022년 7월 8일 오후 6 ~ 10시</S.Text>
      </S.Wrapper>
      <S.Wrapper>
        <LocationSVG width={32} />
        <S.Text>둘둘치킨 선릉공원점</S.Text>
      </S.Wrapper>
      <S.Wrapper>
        <CategorySVG width={32} />
        <S.Text>식사</S.Text>
      </S.Wrapper>
      <S.Wrapper>
        <PersonSVG width={32} />
        <S.Text>4기 이프</S.Text>
      </S.Wrapper>
      <S.JoinButton type="button">참여하기</S.JoinButton>
    </S.Container>
  );
}

export default Info;
