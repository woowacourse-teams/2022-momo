import * as S from './index.styled';

function LocationFallback() {
  return (
    <S.Container>
      <p>지도를 불러오는 중 에러가 발생했어요 😥</p>
    </S.Container>
  );
}

export default LocationFallback;
