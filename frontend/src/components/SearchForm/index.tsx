import { useRef } from 'react';

import { MagnifyingGlassSVG } from 'assets/svg';

import * as S from './index.styled';

const suggestSearchKeyword = [
  '선릉 맛집 탐방',
  '롯데월드 풀코스',
  '야구 보러가요',
  '석촌호수 산책',
  '방탈출 카페 파티 구합니다',
  '모여서 각자 코딩해요',
  '여행을 떠나요',
  '조조영화 같이 보실 분',
  '오늘 같이 칼퇴해요',
];

const randomKeyword =
  suggestSearchKeyword[Math.floor(Math.random() * suggestSearchKeyword.length)];

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
      <S.Input type="text" ref={inputRef} placeholder={randomKeyword} />
    </S.Form>
  );
}

export default SearchForm;
