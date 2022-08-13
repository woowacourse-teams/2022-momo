import * as S from './index.styled';

function SearchForm() {
  return (
    <S.Form>
      <S.Input type="text" />
      <S.Button type="button">🔎</S.Button>
    </S.Form>
  );
}

export default SearchForm;
