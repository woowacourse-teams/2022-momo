import { useRecoilValue } from 'recoil';

import Header from 'components/Header';
import Snackbar from 'components/Snackbar';
import { snackbarState } from 'store/states';

import * as S from './index.styled';

interface PageProps {
  children: React.ReactNode;
}

function Page({ children }: PageProps) {
  const { isShowing, message } = useRecoilValue(snackbarState);

  return (
    <S.PageContainer>
      <Header />
      <S.Content>{children}</S.Content>
      {isShowing && <Snackbar>{message}</Snackbar>}
    </S.PageContainer>
  );
}

export default Page;
