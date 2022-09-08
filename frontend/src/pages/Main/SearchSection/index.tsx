import SearchForm from 'components/SearchForm';

import * as S from './index.styled';

interface SearchSectionProps {
  search: (keyword: string) => void;
}

function SearchSection({ search }: SearchSectionProps) {
  return (
    <S.Container>
      <S.Background />
      <S.Heading>지금 바로 검색해보세요!</S.Heading>
      <SearchForm search={search} />
    </S.Container>
  );
}

export default SearchSection;
