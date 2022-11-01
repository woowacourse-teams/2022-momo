import Signup from 'components/Signup';
import useModal from 'hooks/useModal';
import useMount from 'hooks/useMount';

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

  useMount(() => {
    showLoginModal();
  });

  return (
    <>
      <Login />
      <Signup />
    </>
  );
}

export const Default = Template.bind({});
