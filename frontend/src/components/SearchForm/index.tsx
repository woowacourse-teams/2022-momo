import { useRef } from 'react';

import { MagnifyingGlassSVG } from 'assets/svg';

import * as S from './index.styled';

interface SearchFormProps {
  search: (keyword: string) => void;
}

function SearchForm({ search }: SearchFormProps) {
  const inputRef = useRef<HTMLInputElement>(null);

  const searchWithPreventSubmitEvent = (e: React.FormEvent) => {
    e.preventDefault();

    if (!inputRef.current) return;

    search(inputRef.current.value);
  };

  return (
    <S.Form onSubmit={searchWithPreventSubmitEvent}>
      <MagnifyingGlassSVG />
      <S.Input type="text" ref={inputRef} />
    </S.Form>
  );
}

export default SearchForm;
