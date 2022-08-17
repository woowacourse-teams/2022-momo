import * as S from './index.styled';

interface SearchFormProps {
  keyword: string;
  setKeyword: (e: React.ChangeEvent<HTMLInputElement>) => void;
  search: () => void;
}

function SearchForm({ keyword, setKeyword, search }: SearchFormProps) {
  return (
    <S.Container>
      <S.Input type="text" value={keyword} onChange={setKeyword} />
      <S.Button type="button" onClick={search}>
        🔎
      </S.Button>
    </S.Container>
  );
}

export default SearchForm;
