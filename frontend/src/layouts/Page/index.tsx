import { useRecoilValue } from 'recoil';

import Footer from 'components/Footer';
import Header from 'components/Header';
import Snackbar from 'components/Snackbar';
import { snackbarState } from 'store/states';

import * as S from './index.styled';

interface PageProps {
  children: React.ReactNode;
}

function Page({ children }: PageProps) {
  const { type, isShowing, message } = useRecoilValue(snackbarState);

  return (
    <S.PageContainer>
      <Header />
      <S.Content>{children}</S.Content>
      {isShowing && <Snackbar isError={type === 'error'}>{message}</Snackbar>}
      <Footer />
    </S.PageContainer>
  );
}

export default Page;
