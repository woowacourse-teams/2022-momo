import { NotFoundPage } from 'components/Animation';

import * as S from './index.styled';

function NotFound() {
  return (
    <S.Container>
      <NotFoundPage />
      <S.Title> 404 Not Found </S.Title>
      <S.Description>
        <p>요청하신 페이지를 찾을 수 없어요.</p>
        <p>주소가 올바른지 다시 한 번 확인해주세요.</p>
      </S.Description>
    </S.Container>
  );
}

export default NotFound;
