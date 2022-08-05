import * as S from './index.styled';

function Search() {
  return (
    <S.Container>
      <S.Image />
      <S.Heading>지금 바로 검색해보세요!</S.Heading>
      <S.InputContainer>
        <S.Input type="text" autoFocus />
        <S.Button type="button">🔎</S.Button>
      </S.InputContainer>
    </S.Container>
  );
}

export default Search;
