import useModal from 'hooks/useModal';
import useMount from 'hooks/useMount';

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

  useMount(() => {
    showThumbnailModal();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  });

  return <ImageDropBox {...args} />;
}

export const Default = Template.bind({});

Default.args = {
  id: 1,
};
