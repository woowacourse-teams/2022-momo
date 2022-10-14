import styled from '@emotion/styled';

const BasicContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;

  width: 100%;

  padding: 1rem;
  box-sizing: border-box;
`;

const StickyContainer = styled(BasicContainer)`
  align-items: center;

  position: fixed;
  top: 9rem;

  background: ${({ theme: { colors } }) => colors.white001};

  z-index: 9;
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;

  width: 100%;
`;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;

  width: 85%;
`;

const SideMenu = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;

  height: fit-content;
`;

const ContentContainer = styled(BasicContainer)`
  padding-top: 12rem;
`;

const ControlContainer = styled.div`
  position: sticky;
  bottom: 0;

  z-index: 100;
`;

export {
  StickyContainer,
  Header,
  TitleContainer,
  SideMenu,
  ContentContainer,
  ControlContainer,
};
