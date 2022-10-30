import { useEffect } from 'react';

import useInput from 'hooks/useInput';
import useModal from 'hooks/useModal';

import ConfirmPassword from '.';

const story = {
  title: 'Component/ConfirmPassword',
  component: ConfirmPassword,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template(args) {
  const { showConfirmPasswordModal } = useModal();

  const { value: confirmPassword, setValue: setConfirmPassword } = useInput('');

  useEffect(() => {
    showConfirmPasswordModal();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <ConfirmPassword
      confirmPassword={confirmPassword}
      setConfirmPassword={setConfirmPassword}
      {...args}
    />
  );
}

export const Default = Template.bind({});

Default.args = {
  editPassword: () => {},
};
