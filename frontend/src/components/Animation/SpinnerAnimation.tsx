import Lottie from 'react-lottie';

import * as animationData from '../../assets/spinner.json';

const defaultOptions = {
  loop: true,
  autoplay: true,
  animationData: animationData,
};

function Spinner() {
  return <Lottie options={defaultOptions} height={400} width={400} />;
}

export default Spinner;
