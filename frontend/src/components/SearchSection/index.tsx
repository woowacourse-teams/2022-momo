import * as S from './index.styled';
import SearchForm from './SearchForm';

function SearchSection() {
  return (
    <S.Container>
      <S.Background />
      <S.Heading>지금 바로 검색해보세요!</S.Heading>
      <SearchForm />
    </S.Container>
  );
}

export default SearchSection;
