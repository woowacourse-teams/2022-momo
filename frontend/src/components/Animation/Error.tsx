import Lottie from 'lottie-react';

import error from 'assets/lottie/error.json';

const style = {
  height: 300,
};

function Error() {
  return <Lottie animationData={error} style={style} />;
}

export default Error;
