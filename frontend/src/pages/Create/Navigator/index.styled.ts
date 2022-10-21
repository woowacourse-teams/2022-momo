import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 52px;

  overflow: hidden;

  width: 100%;
  height: fit-content;

  border-radius: 0 0 20px 20px;

  ${({
    theme: {
      colors,
      breakpoints: { md },
    },
  }) => `
    background: ${colors.gray006};

    @media only screen and (min-width: ${md}px) {
      width: ${md}px;
    }
  `}
`;

const PageContentsBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.375rem;

  overflow: hidden;

  width: 100%;
  height: 9rem;

  padding: 0.5rem 0;

  transition: height 0.5s;

  &.closed {
    height: 1.5rem;
  }

  &.step-1 {
    height: 1.5rem;
  }

  &.step-2 {
    button {
      transform: translate3d(0, -1.875rem, 0);
    }
  }

  &.step-3 {
    button {
      transform: translate3d(0, -3.75rem, 0);
    }
  }

  &.step-4 {
    button {
      transform: translate3d(0, -5.625rem, 0);
    }
  }

  &.step-5 {
    button {
      transform: translate3d(0, -7.5rem, 0);
    }
  }
`;

const PageItem = styled.button`
  display: flex;
  align-items: center;

  width: 100%;
  height: 1.5rem;

  padding: 1px 0.75rem;

  transition: transform 0.5s;
`;

const Classification = styled.div`
  width: 15%;

  color: none;
  font-size: 1.2rem;

  ${({ theme: { colors } }) => `
    &.required:first-of-type {
      color: ${colors.green001}
    }
    
    &.not-required:first-of-type {
      color: ${colors.gray002}
    }
  `}
`;

const Content = styled.div`
  display: flex;
  align-items: center;

  width: 60%;

  font-size: 1rem;
`;

const Required = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;

  width: 25%;

  ${({ theme: { colors } }) => `
    svg {
      width: 1.25rem;
      height: 1.25rem;

      padding-right: 0.5rem;

      &.invalid > g > path {
        fill: ${colors.red002}
      }
    }`}
`;

const ToggleButton = styled.button`
  text-align: center;

  z-index: 100;

  padding-bottom: 1rem;

  transition: rotate 0.5s;

  &.closed {
    padding: 1rem 0 0;

    transform: rotate3d(0, 0, 1, 180deg);
  }

  ${({ theme: { colors } }) => `
    background: ${colors.gray006}
  `}
`;

export {
  Container,
  PageContentsBox,
  PageItem,
  Classification,
  Content,
  Required,
  ToggleButton,
};
