import * as S from './index.styled';
import SearchForm from './SearchForm';

interface SearchSectionProps {
  keyword: string;
  setKeyword: (e: React.ChangeEvent<HTMLInputElement>) => void;
  search: () => void;
}

function SearchSection({ keyword, setKeyword, search }: SearchSectionProps) {
  return (
    <S.Container>
      <S.Background />
      <S.Heading>지금 바로 검색해보세요!</S.Heading>
      <SearchForm keyword={keyword} setKeyword={setKeyword} search={search} />
    </S.Container>
  );
}

export default SearchSection;
