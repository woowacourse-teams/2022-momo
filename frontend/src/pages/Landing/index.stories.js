import Landing from '.';

const story = {
  title: 'Page/Landing',
  component: Landing,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template() {
  return <Landing />;
}

export const Default = Template.bind({});
