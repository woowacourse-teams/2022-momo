import { useState } from 'react';

import FilterSection from '.';

const story = {
  title: 'Component/FilterSection',
  component: FilterSection,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

const invalidCategoryId = -1;

function Template(args) {
  const [selectedCategoryId, setSelectedCategoryId] =
    useState(invalidCategoryId);
  const [isExcludeFinished, setIsExcludeFinished] = useState(false);

  const selectCategory = id => () => {
    setSelectedCategoryId(id);
  };

  const toggleIsExcludeFinished = () => {
    setIsExcludeFinished(prevState => !prevState);
  };

  const resetSelectedCategoryId = () => {
    selectCategory(invalidCategoryId)();
  };

  return (
    <FilterSection
      selectedCategoryId={selectedCategoryId}
      selectCategory={selectCategory}
      resetSelectedCategoryId={resetSelectedCategoryId}
      isExcludeFinished={isExcludeFinished}
      toggleIsExcludeFinished={toggleIsExcludeFinished}
      {...args}
    >
      {args.children}
    </FilterSection>
  );
}

export const Default = Template.bind({});

Default.args = {
  search: () => {},
};

export const WithChildren = Template.bind({});

WithChildren.args = {
  search: () => {},
  children: (
    <div style={{ width: '100%', margin: '1rem 0', textAlign: 'center' }}>
      자식 요소는 이 사이에 들어갑니다!
    </div>
  ),
};
