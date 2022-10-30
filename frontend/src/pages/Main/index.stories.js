import PageLayout from 'layouts/Page';

import Main from '.';

const story = {
  title: 'Page/Main',
  component: Main,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template() {
  return (
    <PageLayout>
      <Main />
    </PageLayout>
  );
}

export const Default = Template.bind({});
