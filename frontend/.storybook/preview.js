import { ThemeProvider } from '@emotion/react';
import { RecoilRoot } from 'recoil';
import { QueryClient, QueryClientProvider } from 'react-query';
import { MemoryRouter } from 'react-router-dom';

import { initialize, mswDecorator } from 'msw-storybook-addon';

import theme from 'styles/theme';
import GlobalStyle from 'styles/global';

initialize();

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
    },
  },
});

export const decorators = [
  mswDecorator,
  Story => (
    <ThemeProvider theme={theme}>
      <RecoilRoot>
        <QueryClientProvider client={queryClient}>
          <MemoryRouter initialEntries={['/detail/1']}>
            <GlobalStyle />
            <Story />
          </MemoryRouter>
        </QueryClientProvider>
      </RecoilRoot>
    </ThemeProvider>
  ),
];

export const parameters = {
  actions: { argTypesRegex: '^on[A-Z].*' },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/,
    },
  },
};

if (typeof global.process === 'undefined') {
  const { worker } = require('../src/mocks/browser');
  worker.start({
    onUnhandledRequest: 'bypass',
  });
}
