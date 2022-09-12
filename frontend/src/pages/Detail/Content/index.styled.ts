import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 10px;
`;

const TitleWrapper = styled.div<{ imgSrc: string }>`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1rem;

  min-height: 300px;

  background: ${({ theme: { filter }, imgSrc }) =>
    `linear-gradient(${filter.darken001}, ${filter.darken001}), url(${imgSrc})`};
  background-size: cover;

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
  color: ${({ theme: { colors } }) => colors.yellow001};

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

  color: ${({ theme: { colors } }) => colors.red003};

  font-weight: 700;
`;

const Description = styled(DescriptionBox)`
  align-items: flex-start;

  line-height: 2rem;
  white-space: pre-wrap;

  color: ${({ theme: { colors } }) => colors.black002};
`;

const LocationMap = styled.div<{ imgSrc: string }>`
  width: 32rem;
  height: 32rem;

  background: url(${({ imgSrc }) => imgSrc});
  background-size: cover;
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
