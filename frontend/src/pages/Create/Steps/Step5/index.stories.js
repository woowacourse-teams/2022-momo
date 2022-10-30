import useCreateState from 'hooks/useCreateState';

import Step5 from '.';

const story = {
  title: 'Page/Create/Step5',
  component: Step5,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template() {
  const { useDescriptionState } = useCreateState();

  return <Step5 useDescriptionState={useDescriptionState} />;
}

export const Default = Template.bind({});
