import { forwardRef, LegacyRef } from 'react';

import { Container } from '../@shared/styled';

interface Step3Props {}

function Step3({}: Step3Props, ref: LegacyRef<HTMLDivElement>) {
  return <Container ref={ref}>Step3</Container>;
}

export default forwardRef(Step3);
