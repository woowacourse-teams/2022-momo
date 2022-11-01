import Snackbar from '.';

const story = {
  title: 'Component/Snackbar',
  component: Snackbar,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template(args) {
  return <Snackbar {...args}>이게 우리의 스낵바야!</Snackbar>;
}

export const Basic = Template.bind({});

Basic.args = {
  isError: false,
};

export const Error = Template.bind({});

Error.args = {
  isError: true,
};
