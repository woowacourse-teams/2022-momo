import useAuth from 'hooks/useAuth';
import useMount from 'hooks/useMount';
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

  useMount(() => {
    setLogin('basic', { userId: 'lah1203', name: '하리' });
  });

  return (
    <PageLayout>
      <MyInformation />
    </PageLayout>
  );
}

export const Basic = BasicTemplate.bind({});

function OAuthTemplate() {
  const { setLogin } = useAuth();

  useMount(() => {
    setLogin('oauth', { userId: 'lah1203', name: '하리' });
  });

  return (
    <PageLayout>
      <MyInformation />
    </PageLayout>
  );
}

export const OAuth = OAuthTemplate.bind({});
