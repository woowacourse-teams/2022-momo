import useCreateState from 'hooks/useCreateState';

import Step1 from '.';

const story = {
  title: 'Page/Create/Step1',
  component: Step1,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template(args) {
  const { useNameState, useSelectedCategoryState } = useCreateState();

  return (
    <Step1
      useNameState={useNameState}
      useSelectedCategoryState={useSelectedCategoryState}
      {...args}
    />
  );
}

export const Default = Template.bind({});

Default.args = {
  pressEnterToNext: () => {},
  gotoNextPage: () => {},
};
