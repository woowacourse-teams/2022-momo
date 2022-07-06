import * as S from './index.styled';

function Header() {
  return (
    <S.Container>
      <S.Logo>Momo</S.Logo>
      <S.Nav>
        <div>모임 생성</div>
        <div>내 모임</div>
        <div>회원가입</div>
        <div>로그인</div>
      </S.Nav>
    </S.Container>
  );
}

export default Header;
