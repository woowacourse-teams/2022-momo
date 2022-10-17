import styled from '@emotion/styled';

const Container = styled.div`
  padding-bottom: 52px;
`;

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
  top: calc(5rem + 52px);

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
  padding: 12rem 1rem 4rem;
`;

const ControlContainer = styled.div`
  position: fixed;
  bottom: 52px;

  width: 100%;

  z-index: 100;
`;

export {
  Container,
  StickyContainer,
  Header,
  TitleContainer,
  SideMenu,
  ContentContainer,
  ControlContainer,
};
