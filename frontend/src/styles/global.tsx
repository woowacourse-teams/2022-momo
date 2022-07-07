import { Global, css } from '@emotion/react';
import emotionReset from 'emotion-reset';

import { fontStyle } from './font';
import theme from './theme';

// Heading => SuseongDotum
// Basic => GangwonEdu_OTFBoldA, GangwonEdu_OTFLightA

const style = css`
  ${emotionReset}
  ${fontStyle}

  body {
    font-family: 'GangwonEdu_Bold';
    font-size: 1.1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5 {
    font-family: 'SuseongDotum';
  }

  a {
    text-decoration: none;
    color: ${theme.colors.black002};
  }

  button {
    border: none;

    font-family: 'GangwonEdu_Bold';

    cursor: pointer;
  }

  input {
    font-family: 'GangwonEdu_Bold';
    font-size: 1.1rem;

    &:focus {
      outline: none;
    }
  }
`;

function GlobalStyle() {
  return <Global styles={style} />;
}

export default GlobalStyle;
