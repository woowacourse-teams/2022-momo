import { useEffect } from 'react';

import useModal from 'hooks/useModal';

import ImageDropBox from '.';

const story = {
  title: 'Component/ImageDropBox',
  component: ImageDropBox,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template(args) {
  const { showThumbnailModal } = useModal();

  useEffect(() => {
    showThumbnailModal();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return <ImageDropBox {...args} />;
}

export const Default = Template.bind({});

Default.args = {
  id: 1,
};
