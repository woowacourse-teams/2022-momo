import useCreateState from 'hooks/useCreateState';

import CalendarEditor from '.';

const story = {
  title: 'Component/CalendarEditor',
  component: CalendarEditor,
};

export default story;

function Template(args) {
  const { useScheduleState } = useCreateState();

  return <CalendarEditor useScheduleState={useScheduleState} {...args} />;
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
};
