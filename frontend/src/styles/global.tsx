import { Global, css, useTheme, Theme } from '@emotion/react';
import emotionReset from 'emotion-reset';

import { fontStyle } from './font';

// Heading => SuseongDotum
// Basic => GangwonEdu_OTFBoldA, GangwonEdu_OTFLightA

const style = (theme: Theme) => css`
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
    padding: 0 0.5rem;

    background: ${theme.colors.white001};

    border: 1px solid ${theme.colors.gray003};
    border-radius: 0.5rem;

    font-family: 'GangwonEdu_Bold';
    font-size: 1.2rem;

    &:focus {
      border: 1.5px solid ${theme.colors.green002};

      outline: none;
    }

    &::placeholder {
      color: ${theme.colors.gray003};
    }
  }

  textarea {
    font-family: 'GangwonEdu_Bold';

    &:focus {
      border: 1.5px solid ${theme.colors.green002};

      outline: none;
    }
  }
`;

function GlobalStyle() {
  const theme = useTheme();

  return <Global styles={style(theme)} />;
}

export default GlobalStyle;
