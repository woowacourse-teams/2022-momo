import { useEffect } from 'react';

import useAuth from 'hooks/useAuth';
import PageLayout from 'layouts/Page';

import MyInformation from '.';

const story = {
  title: 'Page/MyInformation',
  component: MyInformation,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function BasicTemplate() {
  const { setLogin } = useAuth();

  useEffect(() => {
    setLogin('basic', { userId: 'lah1203', name: '하리' });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <PageLayout>
      <MyInformation />
    </PageLayout>
  );
}

export const Basic = BasicTemplate.bind({});

function OAuthTemplate() {
  const { setLogin } = useAuth();

  useEffect(() => {
    setLogin('oauth', { userId: 'lah1203', name: '하리' });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <PageLayout>
      <MyInformation />
    </PageLayout>
  );
}

export const OAuth = OAuthTemplate.bind({});
