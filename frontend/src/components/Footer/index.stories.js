import { useEffect } from 'react';

import { INITIAL_VIEWPORTS } from '@storybook/addon-viewport';

import useAuth from 'hooks/useAuth';

import Footer from '.';

const story = {
  title: 'Component/Footer',
  component: Footer,
  parameters: {
    viewport: {
      viewports: INITIAL_VIEWPORTS,
      defaultViewport: 'iphone5',
    },
    layout: 'fullscreen',
  },
};

export default story;

function Template({ isLogin }) {
  const { setLogin, resetAuth } = useAuth();

  useEffect(() => {
    if (!isLogin) {
      resetAuth();
      return;
    }

    setLogin('basic', { userId: 'lah1203', name: '하리' });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isLogin]);

  return <Footer />;
}

export const NotLoggedIn = Template.bind({});

NotLoggedIn.args = {
  isLogin: false,
};

export const LoggedIn = Template.bind({});

LoggedIn.args = {
  isLogin: true,
};
