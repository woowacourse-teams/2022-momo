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

  width: 100%;

  padding: 0.5rem 0;
`;

const Button = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;

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
  padding-top: 12rem;
`;

const Check = styled.div`
  transform: rotate(-45deg);

  width: 0.5rem;
  height: 0.3rem;

  margin-top: 0.2rem;

  ${({ theme: { colors } }) => `
    border-bottom: 2px solid ${colors.gray003};
    border-left: 2px solid ${colors.gray003};

    &.selected {
      border-bottom: 2px solid ${colors.green001};
      border-left: 2px solid ${colors.green001};
    }
  `};
`;

export { SearchWrapper, GroupTypeBox, Button, Content, Check };
