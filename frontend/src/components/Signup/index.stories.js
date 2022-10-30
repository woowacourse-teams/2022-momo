import { useEffect } from 'react';

import useModal from 'hooks/useModal';

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

  useEffect(() => {
    showSignupModal();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return <Signup />;
}

export const Default = Template.bind({});
