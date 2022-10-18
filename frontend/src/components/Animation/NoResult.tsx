import Lottie from 'lottie-react';

import noResult from 'assets/lottie/no_result.json';

const style = {
  width: '100%',
};

function NoResult() {
  return <Lottie animationData={noResult} style={style} />;
}

export default NoResult;
