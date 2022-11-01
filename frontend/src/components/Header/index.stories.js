import { useEffect } from 'react';

import LoginModal from 'components/Login';
import SignupModal from 'components/Signup';
import useAuth from 'hooks/useAuth';

import Header from '.';

const story = {
  title: 'Component/Header',
  component: Header,
  parameters: {
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

  return (
    <>
      <LoginModal />
      <SignupModal />
      <Header />
    </>
  );
}

export const NotLoggedIn = Template.bind({});

NotLoggedIn.args = {
  isLogin: false,
};

export const LoggedIn = Template.bind({});

LoggedIn.args = {
  isLogin: true,
};
