import * as S from './index.styled';

function CategoryFallback() {
  const refreshPage = () => {
    window.location.reload();
  };

  return (
    <S.Container>
      <p>카테고리 목록을 불러오는 중 에러가 발생했어요 😥</p>
      <S.RefreshButton onClick={refreshPage}>새로고침</S.RefreshButton>
    </S.Container>
  );
}

export { CategoryFallback };
