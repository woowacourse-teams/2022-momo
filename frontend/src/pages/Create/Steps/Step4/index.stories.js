import useCreateState from 'hooks/useCreateState';

import Step4 from '.';

const story = {
  title: 'Page/Create/Step4',
  component: Step4,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template(args) {
  const { useCapacityState, useLocationState } = useCreateState();

  return (
    <Step4
      useCapacityState={useCapacityState}
      useLocationState={useLocationState}
      {...args}
    />
  );
}

export const Default = Template.bind({});

Default.args = {
  pressEnterToNext: () => {},
};
