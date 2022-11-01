import Modal from '.';

const story = {
  title: 'Component/Modal',
  component: Modal,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template(args) {
  return <Modal {...args}>안녕, 나는 모달이야!</Modal>;
}

export const Default = Template.bind({});

Default.args = {
  modalState: true,
};
