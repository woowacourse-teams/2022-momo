import { Global, css, useTheme, Theme } from '@emotion/react';
import emotionReset from 'emotion-reset';

import { fontStyle } from './font';

// Heading => SuseongDotum
// Basic => GangwonEdu_OTFBoldA

const style = (colors: Theme['colors']) => css`
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
    color: ${colors.black002};
  }

  button {
    border: none;

    font-family: 'GangwonEdu_Bold';

    cursor: pointer;
  }

  input {
    padding: 0 0.5rem;

    background: ${colors.white001};

    border: 1px solid ${colors.gray003};
    border-radius: 0.5rem;

    box-sizing: border-box;

    font-family: 'GangwonEdu_Bold';
    font-size: 1.2rem;

    &:disabled {
      background: ${colors.gray005};
    }

    &:focus {
      border: 1.5px solid ${colors.green002};

      outline: none;
    }

    &::placeholder {
      color: ${colors.gray003};
    }
  }

  textarea {
    font-family: 'GangwonEdu_Bold';

    &:focus {
      border: 1.5px solid ${colors.green002};

      outline: none;
    }
  }
`;

function GlobalStyle() {
  const theme = useTheme();

  return <Global styles={style(theme.colors)} />;
}

export default GlobalStyle;
