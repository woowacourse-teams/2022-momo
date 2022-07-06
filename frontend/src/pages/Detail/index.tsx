import { DetailSideBar, DetailContent } from 'components/Detail';

import * as S from './index.styled';

function Detail() {
  return (
    <>
      <S.PageContainer>
        <DetailSideBar />
        <DetailContent />
      </S.PageContainer>
    </>
  );
}

export default Detail;
