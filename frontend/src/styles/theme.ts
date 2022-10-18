import { Theme } from '@emotion/react';

const breakpoints = {
  sm: 425,
  md: 768,
  lg: 1100,
  xl: 1440,
};

const theme: Theme = {
  breakpoints,
  colors: {
    black001: '#000000',
    black002: '#454545',
    white001: '#FFFFFF',
    gray001: '#777777',
    gray002: '#CCCCCC',
    gray003: '#DDDDDD',
    gray004: '#E7E7E7',
    gray005: '#EEEEEE',
    gray006: '#F6F6F6',
    yellow001: '#FFC500',
    yellow002: '#FFE47A',
    green001: '#85AD61',
    green002: '#A5CC82',
    blue001: '#00467F',
    blue002: '#3D7EAA',
    red001: '#C21500',
    red002: '#E05A5E',
    red003: '#F7797D',
  },
  filter: {
    darken001: '#0000004D',
  },
};

export default theme;
