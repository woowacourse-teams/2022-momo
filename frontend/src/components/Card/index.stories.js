import Card from '.';

const story = {
  title: 'Component/Card',
  component: Card,
};

export default story;

function Template(args) {
  return <Card {...args} />;
}

const today = new Date();

export const Default = Template.bind({});

Default.args = {
  group: {
    id: 1,
    name: '기본 모임이에요 👑',
    host: { id: 1, name: '하리' },
    deadline: new Date(
      today.getFullYear(),
      today.getMonth(),
      today.getDate() + 7,
    )
      .toISOString()
      .split('T')[0],
    finished: false,
    numOfParticipant: 5,
    capacity: 10,
    like: false,
    imageUrl: 'https://image.moyeora.site/group/default/thumbnail_study.jpg',
  },
};

export const Finished = Template.bind({});

Finished.args = {
  group: {
    id: 1,
    name: '마감된 모임이에요 👋',
    host: { id: 1, name: '하리' },
    deadline: new Date(
      today.getFullYear(),
      today.getMonth(),
      today.getDate() + 7,
    )
      .toISOString()
      .split('T')[0],
    finished: true,
    numOfParticipant: 5,
    capacity: 10,
    like: false,
    imageUrl: 'https://image.moyeora.site/group/default/thumbnail_study.jpg',
  },
};

export const Liked = Template.bind({});

Liked.args = {
  group: {
    id: 1,
    name: '찜한 모임이에요 ❤️',
    host: { id: 1, name: '하리' },
    deadline: new Date(
      today.getFullYear(),
      today.getMonth(),
      today.getDate() + 7,
    )
      .toISOString()
      .split('T')[0],
    finished: false,
    numOfParticipant: 5,
    capacity: 10,
    like: true,
    imageUrl: 'https://image.moyeora.site/group/default/thumbnail_study.jpg',
  },
};
