import * as S from './index.styled';

function CategoryFallback() {
  return (
    <S.CategoryContainer>
      <p>카테고리 목록을 불러오는 중 에러가 발생했어요 😥</p>
      <S.RefreshButton onClick={() => window.location.reload()}>
        새로고침
      </S.RefreshButton>
    </S.CategoryContainer>
  );
}

export { CategoryFallback };
