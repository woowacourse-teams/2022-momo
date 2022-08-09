import Lottie from 'lottie-react';

import noResult from 'assets/no_result.json';

const style = { width: '50rem' };

function NoResult() {
  return <Lottie animationData={noResult} style={style} />;
}

export default NoResult;
