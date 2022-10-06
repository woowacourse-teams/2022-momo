import styled from '@emotion/styled';

const Image = styled.div<{ src: string }>`
  position: sticky;
  top: 4rem;

  width: 100%;
  height: 5rem;

  background: ${({ theme: { filter }, src }) =>
    `linear-gradient(${filter.darken001}, ${filter.darken001}), url(${src})`};
  background-size: cover;
  background-position: center;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;

  padding: 1rem;
`;

const ContentContainer = styled.div`
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 2rem;

  width: 1000px;
`;

const StickyContainer = styled(ContentContainer)`
  align-items: center;

  position: sticky;
  top: 11rem;

  background: ${({ theme: { colors } }) => colors.white001};

  z-index: 9;
`;

const Header = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  margin: 1rem 0;
`;

const Category = styled.div`
  color: ${({ theme: { colors } }) => colors.blue001};

  font-weight: 900;
`;

const Title = styled.h2`
  line-height: 2rem;

  font-size: 1.7rem;
`;

const Duration = styled.div`
  align-items: center;

  color: ${({ theme: { colors } }) => colors.red003};

  font-weight: 700;
`;

export {
  Image,
  Container,
  ContentContainer,
  StickyContainer,
  Header,
  Category,
  Title,
  Duration,
};
