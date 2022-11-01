import PageLayout from 'layouts/Page';

import Create from '.';

const story = {
  title: 'Page/Create',
  component: Create,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template() {
  return (
    <PageLayout>
      <Create />
    </PageLayout>
  );
}

export const Default = Template.bind({});
