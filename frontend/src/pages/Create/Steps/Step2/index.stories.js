import useCreateState from 'hooks/useCreateState';

import Step2 from '.';

const story = {
  title: 'Page/Create/Step2',
  component: Step2,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template(args) {
  const { useDateState, useDeadlineState, isEmptyInput } = useCreateState();

  const getValidateState = pageIndex => {
    // eslint-disable-next-line default-case
    switch (pageIndex) {
      case 1:
        if (!isEmptyInput.name) return 'invalid';
        break;
      case 2:
        if (
          !(
            isEmptyInput.startDate &&
            isEmptyInput.endDate &&
            isEmptyInput.deadline
          )
        )
          return 'invalid';
    }
    return '';
  };

  return (
    <Step2
      useDateState={useDateState}
      useDeadlineState={useDeadlineState}
      getValidateState={getValidateState}
      {...args}
    />
  );
}

export const Default = Template.bind({});

Default.args = {
  pressEnterToNext: () => {},
};
