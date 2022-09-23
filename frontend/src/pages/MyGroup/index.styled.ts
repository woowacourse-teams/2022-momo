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
  cursor: pointer;

  ${({ theme: { colors } }) => `
    color: ${colors.gray002};
    
    &.selected {
      color: ${colors.black001};
    }
  `}
`;

const Content = styled.div`
  width: 100%;

  margin: 2rem auto;

  ${({
    theme: {
      breakpoints: { sm, md, lg, xl },
    },
  }) => `
    @media only screen and (max-width: ${md}px) {
      align-items: center;
      max-width: ${sm}px;
    }

    @media only screen and (min-width: ${md}px) and (max-width: ${lg}px) {
      max-width: ${md}px;
    }

    @media only screen and (min-width: ${lg}px) and (max-width: ${xl}px) {
      max-width: ${lg}px;
    }

    @media only screen and (min-width: ${xl}px) {
      max-width: ${xl}px;
    }
  `}
`;

export { SearchWrapper, GroupTypeBox, Button, Content };
