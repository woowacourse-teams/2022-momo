import useInput from 'hooks/useInput';
import useModal from 'hooks/useModal';
import useMount from 'hooks/useMount';

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

  useMount(() => {
    showConfirmPasswordModal();
  });

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
