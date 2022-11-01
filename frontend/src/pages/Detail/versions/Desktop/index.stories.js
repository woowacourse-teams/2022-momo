import { Route, Routes } from 'react-router-dom';

import PageLayout from 'layouts/Page';
import Detail from 'pages/Detail';

const story = {
  title: 'Page/Detail',
  component: Detail,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function Template() {
  return (
    <PageLayout>
      <Routes>
        <Route element={<Detail />} path="/detail/:id" />
      </Routes>
    </PageLayout>
  );
}

export const Desktop = Template.bind({ id: 1 });
