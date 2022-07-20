import { useNavigate } from 'react-router-dom';

import { deleteGroup as requestDeleteGroup } from 'apis/request/group';
import { ReactComponent as ClockSVG } from 'assets/clock.svg';
import { ReactComponent as LocationSVG } from 'assets/location.svg';
import CategorySVG from 'components/svg/Category';
import PersonSVG from 'components/svg/Person';
import { ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import { CategoryType, DetailData } from 'types/data';

import * as S from './index.styled';

// TODO: 시간 제외 날짜 파싱, 이후에 달력과 함께 표기
const parsedDate = (schedules: DetailData['schedules']) =>
  '2022년 12월 25일 오후 6 ~ 10시';

function Info({
  id,
  name,
  schedules,
  categoryName,
  location,
}: Pick<DetailData, 'id' | 'name' | 'schedules' | 'location'> & {
  categoryName: CategoryType['name'];
}) {
  const navigate = useNavigate();
  const deleteGroup = () => {
    if (!window.confirm(GUIDE_MESSAGE.DELETE.CONFIRM_REQUEST)) return;

    requestDeleteGroup(id)
      .then(() => {
        alert(GUIDE_MESSAGE.DELETE.SUCCESS_REQUEST);
        navigate('/');
      })
      .catch(() => {
        alert(ERROR_MESSAGE.DELETE.FAILURE_REQUEST);
      });
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
      <S.JoinButton type="button" onClick={deleteGroup}>
        삭제하기
      </S.JoinButton>
    </S.Container>
  );
}

export default Info;
