import backgroundImage from 'assets/drunk_two.jpg';

import * as S from './index.styled';

function Search() {
  return (
    <S.Container>
      <S.Image src={backgroundImage} alt="검색 영역 배경" />
      <S.Heading>지금 바로 검색해보세요!</S.Heading>
      <S.InputContainer>
        <S.Input type="text" autoFocus />
        <S.Button type="button">검색</S.Button>
      </S.InputContainer>
    </S.Container>
  );
}

export default Search;
