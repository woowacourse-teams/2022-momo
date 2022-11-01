import Participants from '.';

const story = {
  title: 'Page/Detail/Participants',
  component: Participants,
};

export default story;

function Template(args) {
  return (
    <div style={{ width: '90%', maxWidth: '20rem' }}>
      <Participants {...args} />
    </div>
  );
}

export const Default = Template.bind({});

Default.args = {
  host: { id: 68, name: '팰린드롬' },
  capacity: 99,
  participants: [
    { id: 68, name: '팰린드롬' },
    { id: 2, name: '유세지' },
    { id: 74, name: '?!@?#!?@#?~?@#?!?@#?' },
    { id: 80, name: 'moyeora' },
  ],
};
