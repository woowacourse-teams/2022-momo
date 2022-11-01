import useModal from 'hooks/useModal';
import useMount from 'hooks/useMount';

import Signup from '.';

const story = {
  title: 'Component/Signup',
  component: Signup,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template() {
  const { showSignupModal } = useModal();

  useMount(() => {
    showSignupModal();
  });

  return <Signup />;
}

export const Default = Template.bind({});
