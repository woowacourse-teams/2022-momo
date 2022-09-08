import styled from '@emotion/styled';

const SearchWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  height: 10rem;
`;

const GroupTypeBox = styled.div`
  display: flex;
  justify-content: center;

  gap: 3rem;
`;

const Button = styled.div`
  color: ${({ theme: { colors } }) => colors.gray002};

  cursor: pointer;

  &.selected {
    color: ${({ theme: { colors } }) => colors.black001};
  }
`;

const Content = styled.div`
  width: 100%;

  margin: 2rem auto;

  ${({ theme: { breakpoints } }) => `
    @media only screen and (max-width: ${breakpoints.md}px) {
      align-items: center;
      max-width: ${breakpoints.sm}px;
    }

    @media only screen and (min-width: ${breakpoints.md}px) and (max-width: ${breakpoints.lg}px) {
      max-width: ${breakpoints.md}px;
    }

    @media only screen and (min-width: ${breakpoints.lg}px) and (max-width: ${breakpoints.xl}px) {
      max-width: ${breakpoints.lg}px;
    }

    @media only screen and (min-width: ${breakpoints.xl}px) {
      max-width: ${breakpoints.xl}px;
    }
  `}
`;

export { SearchWrapper, GroupTypeBox, Button, Content };
