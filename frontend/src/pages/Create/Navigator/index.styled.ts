import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;

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
  gap: 6px;

  overflow: hidden;

  width: 100%;
  height: 144px;

  padding: 0.5rem 0;

  transition: height 0.5s;

  &.closed {
    height: 24px;
  }

  &.step-1 {
    height: 24px;
  }

  &.step-2 {
    button {
      transform: translate3d(0, -30px, 0);
    }
  }

  &.step-3 {
    button {
      transform: translate3d(0, -60px, 0);
    }
  }

  &.step-4 {
    button {
      transform: translate3d(0, -90px, 0);
    }
  }

  &.step-5 {
    button {
      transform: translate3d(0, -120px, 0);
    }
  }
`;

const PageItem = styled.button`
  display: flex;
  align-items: center;

  width: 100%;
  height: 24px;

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
    }`}
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
      width: 20px;

      padding-right: 0.5rem;

      &.invalid > g > path {
        fill: ${colors.red002}
      }
    }`}
`;

const ToggleButton = styled.button`
  text-align: center;

  z-index: 100;

  padding: 0 0 1rem;

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
