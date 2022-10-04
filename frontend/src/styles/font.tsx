import { css } from '@emotion/react';

const { origin } = window.location;

const fontStyle = css`
  @font-face {
    font-family: 'GangwonEdu_Bold';
    src: url('${origin}/font/GangwonEdu_Bold.woff2') format('woff2'),
      url('${origin}/font/GangwonEdu_Bold.ttf') format('truetype');
    font-weight: normal;
    font-style: normal;
    font-display: fallback;
  }

  @font-face {
    font-family: 'SuseongDotum';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2205@1.0/SuseongDotum.woff2')
        format('woff2'),
      url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2205@1.0/SuseongDotum.ttf')
        format('ttf');
    font-weight: normal;
    font-style: normal;
    font-display: fallback;
  }
`;

export { fontStyle };
