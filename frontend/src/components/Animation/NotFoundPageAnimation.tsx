import Lottie from 'lottie-react';

import notFound from 'assets/not_found.json';

const style = {
  height: 300,
};

function NotFoundPage() {
  return <Lottie animationData={notFound} style={style} />;
}

export default NotFoundPage;
