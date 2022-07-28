import { NotFoundPage } from 'components/Animation';

import * as S from './index.styled';

function NotFound() {
  return (
    <S.PageContainer>
      <NotFoundPage />
      <S.PageTitle> 404 Not Found </S.PageTitle>
      <S.PageDescription>
        요청하신 페이지를 찾을 수 없어요. 주소가 올바른지 다시 한 번
        확인해주세요.
      </S.PageDescription>
    </S.PageContainer>
  );
}

export default NotFound;
