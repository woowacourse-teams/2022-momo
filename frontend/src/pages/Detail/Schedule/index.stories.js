import Schedule from '.';

const story = {
  title: 'Page/Detail/Schedule',
  component: Schedule,
};

export default story;

function Template(args) {
  return (
    <div style={{ width: '90%', maxWidth: '20rem' }}>
      <Schedule {...args} />
    </div>
  );
}

export const Default = Template.bind({});
const today = new Date();

Default.args = {
  duration: {
    start: new Date(today.getFullYear(), today.getMonth(), today.getDate() - 7)
      .toISOString()
      .split('T')[0],
    end: new Date(today.getFullYear(), today.getMonth(), today.getDate() + 7)
      .toISOString()
      .split('T')[0],
  },
  schedules: [
    {
      date: new Date(today.getFullYear(), today.getMonth(), today.getDate() - 2)
        .toISOString()
        .split('T')[0],
      startTime: '10:30',
      endTime: '20:30',
    },
    {
      date: new Date(today.getFullYear(), today.getMonth(), today.getDate() - 1)
        .toISOString()
        .split('T')[0],
      startTime: '10:30',
      endTime: '20:30',
    },
    {
      date: new Date(today.getFullYear(), today.getMonth(), today.getDate())
        .toISOString()
        .split('T')[0],
      startTime: '10:30',
      endTime: '20:30',
    },
    {
      date: new Date(today.getFullYear(), today.getMonth(), today.getDate() + 2)
        .toISOString()
        .split('T')[0],
      startTime: '10:30',
      endTime: '20:30',
    },
  ],
};
