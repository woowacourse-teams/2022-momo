import '@emotion/react';

declare module '@emotion/react' {
  export interface Theme {
    breakpoints: Record<'sm' | 'md' | 'lg' | 'xl', number>;
    colors: Record<
      | 'black001'
      | 'black002'
      | 'white001'
      | 'gray001'
      | 'gray002'
      | 'gray003'
      | 'gray004'
      | 'gray005'
      | 'gray006'
      | 'yellow001'
      | 'yellow002'
      | 'green001'
      | 'green002'
      | 'blue001'
      | 'blue002'
      | 'red001'
      | 'red002'
      | 'red003',
      string
    >;
    filter: Record<'darken001', string>;
  }
}

declare global {
  interface Window {
    kakao: any;
  }
}
