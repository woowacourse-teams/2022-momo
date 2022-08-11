import Lottie from 'lottie-react';

import loading from 'assets/lottie/loading.json';

const style = {
  width: '10rem',
};

function Loading() {
  return <Lottie animationData={loading} style={style} />;
}

export default Loading;
