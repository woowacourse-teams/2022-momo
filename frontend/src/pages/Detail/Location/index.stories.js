import Location from '.';

const story = {
  title: 'Page/Detail/Location',
  component: Location,
};

export default story;

function Template(args) {
  return (
    <div style={{ width: '90%', maxWidth: '25rem' }}>
      <Location {...args} />
    </div>
  );
}

export const Default = Template.bind({});

Default.args = {
  location: {
    address: '서울 관악구 낙성대역3길 3',
    buildingName: '',
    detail: '서울역',
  },
};
