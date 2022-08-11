import Lottie from 'lottie-react';

import spinner from 'assets/lottie/spinner.json';

const style = {
  height: 300,
};

function Spinner() {
  return <Lottie animationData={spinner} style={style} />;
}

export default Spinner;
