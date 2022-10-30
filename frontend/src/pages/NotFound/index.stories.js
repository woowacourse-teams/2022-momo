import PageLayout from 'layouts/Page';

import NotFound from '.';

const story = {
  title: 'Page/NotFound',
  component: NotFound,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template() {
  return (
    <PageLayout>
      <NotFound />
    </PageLayout>
  );
}

export const Default = Template.bind({});
