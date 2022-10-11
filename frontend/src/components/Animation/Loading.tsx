import Lottie from 'lottie-react';

import loading from 'assets/lottie/loading.json';

const style = {
  width: '20%',
  maxHeight: '5rem',
};

function Loading() {
  return <Lottie animationData={loading} style={style} />;
}

export default Loading;
