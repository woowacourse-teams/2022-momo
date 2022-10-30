import { useState } from 'react';

import Checkbox from '.';

const story = {
  title: 'Component/Checkbox',
  component: Checkbox,
};

export default story;

function Template(args) {
  const [checked, setChecked] = useState(false);

  const toggleChecked = () => {
    setChecked(prevState => !prevState);
  };

  return <Checkbox checked={checked} toggleChecked={toggleChecked} {...args} />;
}

export const Default = Template.bind({});

Default.args = {
  description: '저를 눌러보세요!',
};
