import { useEffect } from 'react';

import Signup from 'components/Signup';
import useModal from 'hooks/useModal';

import Login from '.';

const story = {
  title: 'Component/Login',
  component: Login,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template() {
  const { showLoginModal } = useModal();

  useEffect(() => {
    showLoginModal();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      <Login />
      <Signup />
    </>
  );
}

export const Default = Template.bind({});
