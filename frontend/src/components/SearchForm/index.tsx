import * as S from './index.styled';

interface SearchFormProps {
  keyword: string;
  setKeyword: (e: React.ChangeEvent<HTMLInputElement>) => void;
  search: () => void;
}

function SearchForm({ keyword, setKeyword, search }: SearchFormProps) {
  const searchWithPreventSubmitEvent = (e: React.FormEvent) => {
    e.preventDefault();

    search();
  };

  return (
    <S.Form onSubmit={searchWithPreventSubmitEvent}>
      <S.Input type="text" value={keyword} onChange={setKeyword} />
      <S.Button type="submit">🔎</S.Button>
    </S.Form>
  );
}

export default SearchForm;
