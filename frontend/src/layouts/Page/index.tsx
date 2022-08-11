import Header from 'components/Header';

import * as S from './index.styled';

interface PageProps {
  children: React.ReactNode;
}

function Page({ children }: PageProps) {
  return (
    <S.PageContainer>
      <Header />
      <S.Content>{children}</S.Content>
    </S.PageContainer>
  );
}

export default Page;
