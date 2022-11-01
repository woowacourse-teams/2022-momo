import { useState } from 'react';

import useCreateState from 'hooks/useCreateState';

import Navigator from '.';

const story = {
  title: 'Page/Create/Navigator',
  component: Navigator,
};

export default story;

function Template(args) {
  const [page, setPage] = useState(1);
  const { isEmptyInput } = useCreateState();

  const getValidateState = pageIndex => {
    // eslint-disable-next-line default-case
    switch (pageIndex) {
      case 1:
        if (!isEmptyInput.name) return 'invalid';
        break;
      case 2:
        if (
          !(
            isEmptyInput.startDate &&
            isEmptyInput.endDate &&
            isEmptyInput.deadline
          )
        )
          return 'invalid';
    }
    return '';
  };

  return (
    <Navigator
      page={page}
      setPage={setPage}
      getValidateState={getValidateState}
      {...args}
    />
  );
}

export const Default = Template.bind({});

Default.args = {
  totalPage: [
    { number: 1, content: '이름 입력 / 카테고리 선택', required: true },
    { number: 2, content: '기간 입력 / 마감일 입력', required: true },
    { number: 3, content: '일정 입력', required: false },
    { number: 4, content: '장소 입력 / 최대 인원 입력', required: false },
    { number: 5, content: '설명 입력', required: false },
  ],
};
