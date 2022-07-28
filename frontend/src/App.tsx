import { Suspense } from 'react';

import { ThemeProvider } from '@emotion/react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import ScrollToTop from 'components/@shared/ScrollToTop';
import ErrorBoundary from 'components/ErrorBoundary';
import Loading from 'components/Loading';
import LoginModal from 'components/Login';
import SignupModal from 'components/Signup';
import { BROWSER_PATH } from 'constants/path';
import PageLayout from 'layouts/Page';
import { Main, Detail, Create } from 'pages/index';
import GlobalStyle from 'styles/global';
import theme from 'styles/theme';

const queryClient = new QueryClient();

function App() {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <RecoilRoot>
        <QueryClientProvider client={queryClient}>
          <Router>
            <ScrollToTop />
            <PageLayout>
              <SignupModal />
              <LoginModal />
              <ErrorBoundary>
                <Suspense fallback={<Loading />}>
                  <Routes>
                    <Route path={BROWSER_PATH.BASE} element={<Main />} />
                    <Route path={BROWSER_PATH.DETAIL}>
                      <Route path=":id" element={<Detail />} />
                    </Route>
                    <Route path={BROWSER_PATH.CREATE} element={<Create />} />
                  </Routes>
                </Suspense>
              </ErrorBoundary>
            </PageLayout>
          </Router>
        </QueryClientProvider>
      </RecoilRoot>
    </ThemeProvider>
  );
}

export default App;
