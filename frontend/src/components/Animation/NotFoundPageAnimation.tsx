import Lottie from 'react-lottie';

import * as animationData from 'assets/not_found.json';

const defaultOptions = {
  loop: true,
  autoplay: true,
  animationData: animationData,
};

function NotFoundPage() {
  return <Lottie options={defaultOptions} height={400} width={400} />;
}

export default NotFoundPage;
