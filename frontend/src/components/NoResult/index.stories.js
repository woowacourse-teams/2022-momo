import NoResult from '.';

const story = {
  title: 'Component/NoResult',
  component: NoResult,
};

export default story;

function Template() {
  return (
    <div style={{ width: '100%', maxWidth: '50rem' }}>
      <NoResult>
        찾고 계신 모임이 없어요 ・゜・(ノД`)
        <br />
        새로운 모임을 추가해보는 건 어떨까요?
      </NoResult>
    </div>
  );
}

export const Default = Template.bind({});
