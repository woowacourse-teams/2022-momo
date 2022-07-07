import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 10px;
`;

const TitleWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1rem;

  min-height: 300px;

  background: ${({ theme: { colors } }) => colors.gray003};

  border-radius: 10px 10px 0 0;
`;

const DescriptionContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.25rem;

  padding: 1.25rem;
`;

const Hashtag = styled.p`
  color: ${({ theme: { colors } }) => colors.blue002};

  font-weight: 700;
`;

const Title = styled.h2`
  color: ${({ theme: { colors } }) => colors.white001};

  font-size: 2rem;
  font-weight: 900;
`;

const DescriptionBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;

  width: 70%;
  max-width: 46.875rem;

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 10px;

  padding: 15px;
`;

const Duration = styled(DescriptionBox)`
  align-items: center;

  color: ${({ theme: { colors } }) => colors.red002};

  font-weight: 700;
`;

const Description = styled(DescriptionBox)`
  align-items: flex-start;

  line-height: 2rem;

  color: ${({ theme: { colors } }) => colors.black002};
`;

const LocationMap = styled.div`
  width: 32rem;
  height: 32rem;

  background: ${({ theme: { colors } }) => colors.gray005};
`;

export {
  Container,
  TitleWrapper,
  DescriptionContainer,
  Hashtag,
  Title,
  Duration,
  Description,
  LocationMap,
};
