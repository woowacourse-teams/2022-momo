import SearchForm from 'components/SearchForm';

import * as S from './index.styled';

interface SearchSectionProps {
  search: (keyword: string) => void;
}

function SearchSection({ search }: SearchSectionProps) {
  return (
    <S.Wrapper>
      <SearchForm search={search} />
    </S.Wrapper>
  );
}

export default SearchSection;
