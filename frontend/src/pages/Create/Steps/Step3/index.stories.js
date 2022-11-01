import useCreateState from 'hooks/useCreateState';

import Step3 from '.';

const story = {
  title: 'Page/Create/Step3',
  component: Step3,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template(args) {
  const { useScheduleState } = useCreateState();

  return <Step3 useScheduleState={useScheduleState} {...args} />;
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
  pressEnterToNext: () => {},
};
