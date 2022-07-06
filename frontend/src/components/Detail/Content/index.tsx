import * as S from './index.styled';

function DetailContent() {
  return (
    <S.Container>
      <S.TitleWrapper>
        <S.Hashtag>#한 잔 #음주 #치킨</S.Hashtag>
        <S.Title>오늘 끝나고 맥주 드실 분</S.Title>
      </S.TitleWrapper>
      <S.DescriptionContainer>
        <S.Duration>⏳ 모집 마감까지 06:30:33 남았습니다</S.Duration>
        <S.Description>
          <p>오늘로 레벨 3의 첫 데모 데이가 끝납니다.</p>
          <p>
            저녁식사 하면서 간단하게 한 잔 하려고 하는데 오실 분들은 자유롭게
            참여해주세요.
          </p>
          <p>메뉴는 치킨에 맥주입니다. </p>
          <p>술 강요 없음 / 딱 한 잔 가능 / 주종 선택 자유</p>
        </S.Description>
        <S.LocationMap />
      </S.DescriptionContainer>
    </S.Container>
  );
}

export default DetailContent;
