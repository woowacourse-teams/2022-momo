import useDate from 'hooks/useDate';

import Calendar from '.';

const story = {
  title: 'Component/Calendar',
  component: Calendar,
};

export default story;

function Template(args) {
  const { year, month, goToPrevMonth, goToNextMonth } = useDate();

  return (
    <div style={{ width: '100%', maxWidth: '20rem' }}>
      <Calendar
        year={year}
        month={month}
        goToPrevMonth={goToPrevMonth}
        goToNextMonth={goToNextMonth}
        {...args}
      />
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
      date: new Date(today.getFullYear(), today.getMonth(), today.getDate())
        .toISOString()
        .split('T')[0],
      startTime: '10:30',
      endTime: '20:30',
    },
  ],
};
