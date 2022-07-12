import '@emotion/react';

declare module '@emotion/react' {
  export interface Theme {
    breakpoints: {
      sm: number;
      md: number;
      lg: number;
      xl: number;
    };
    colors: {
      black001: string;
      black002: string;
      white001: string;
      gray001: string;
      gray002: string;
      gray003: string;
      gray004: string;
      gray005: string;
      yellow001: string;
      yellow002: string;
      green001: string;
      blue001: string;
      blue002: string;
      red001: string;
      red002: string;
    };
  }
}
