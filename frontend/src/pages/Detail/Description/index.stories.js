import Description from '.';

const story = {
  title: 'Page/Detail/Description',
  component: Description,
};

export default story;

function Template(args) {
  return <Description {...args} />;
}

export const NoneDetail = Template.bind({});

NoneDetail.args = {
  type: 'detail',
};

export const NoneLocation = Template.bind({});

NoneLocation.args = {
  type: 'location',
};
