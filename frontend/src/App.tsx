import { ThemeProvider } from '@emotion/react';
import PageLayout from 'layouts/Page';
import { Main } from 'pages';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import GlobalStyle from 'styles/global';
import theme from 'styles/theme';

import { BROWSER_PATH } from 'constants/path';

const queryClient = new QueryClient();

function App() {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <RecoilRoot>
        <QueryClientProvider client={queryClient}>
          <PageLayout>
            <Router>
              <Routes>
                <Route path={BROWSER_PATH.BASE} element={<Main />} />
              </Routes>
            </Router>
          </PageLayout>
        </QueryClientProvider>
      </RecoilRoot>
    </ThemeProvider>
  );
}

export default App;
