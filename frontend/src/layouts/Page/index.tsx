import { useRecoilValue } from 'recoil';

import Snackbar from 'components/@shared/Snackbar';
import Header from 'components/Header';
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
