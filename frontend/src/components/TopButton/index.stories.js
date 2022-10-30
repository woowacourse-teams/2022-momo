import TopButton from '.';

const story = {
  title: 'Component/TopButton',
  component: TopButton,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template() {
  return <TopButton />;
}

export const Default = Template.bind({});
