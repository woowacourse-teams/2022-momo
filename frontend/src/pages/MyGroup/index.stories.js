import PageLayout from 'layouts/Page';

import MyGroup from '.';

const story = {
  title: 'Page/MyGroup',
  component: MyGroup,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template() {
  return (
    <PageLayout>
      <MyGroup />
    </PageLayout>
  );
}

export const Default = Template.bind({});
