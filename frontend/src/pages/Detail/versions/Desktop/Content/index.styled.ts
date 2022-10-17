import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;

  padding: 0 1rem 1rem;
`;

const BasicContainer = styled.div`
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 2rem;

  width: 47rem;
`;

const StickyContainer = styled(BasicContainer)`
  align-items: center;

  position: fixed;
  top: calc(5rem + 52px);

  background: ${({ theme: { colors } }) => colors.white001};

  z-index: 9;
`;

const ContentContainer = styled(BasicContainer)`
  padding-top: 14rem;
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;

  width: 25rem;

  margin: 1rem 0;
`;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.9rem;

  width: 90%;
`;

export { Container, StickyContainer, ContentContainer, Header, TitleContainer };
