import useModal from 'hooks/useModal';
import useMount from 'hooks/useMount';

import Postcode from '.';

const story = {
  title: 'Component/Postcode',
  component: Postcode,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template(args) {
  const { showPostcodeModal } = useModal();

  useMount(() => {
    showPostcodeModal();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  });

  return <Postcode {...args} />;
}

export const Default = Template.bind({});

Default.args = {
  completeFunc: address => {},
};
