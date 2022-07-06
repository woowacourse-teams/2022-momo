import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;

  width: 21.875rem;
  height: 18.75rem;

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 20px;

  cursor: pointer;
`;

const Image = styled.div`
  width: 100%;
  height: 60%;

  background: ${({ theme: { colors } }) => colors.gray001};

  border-radius: 20px 20px 0 0;
`;

const Description = styled.div`
  display: flex;
  justify-content: space-between;

  padding: 1rem;
`;

const Left = styled.div`
  max-width: 58%;
`;

const Title = styled.div`
  display: -webkit-box;

  overflow: hidden;
  text-overflow: ellipsis;
  word-wrap: break-word;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;

  font-weight: 700;
  font-size: 1rem;
`;

const HostName = styled.div`
  color: ${({ theme: { colors } }) => colors.black002};

  font-weight: 500;
  font-size: 1rem;
`;

const HashtagBox = styled.div`
  display: flex;
  gap: 0.3rem;

  margin-top: 1.875rem;
`;

const Hashtag = styled.div`
  color: ${({ theme: { colors } }) => colors.blue002};

  font-weight: 700;
  font-size: 1rem;
`;

const Deadline = styled.div`
  text-align: right;

  min-width: 40%;
  max-width: 80%;

  color: ${({ theme: { colors } }) => colors.red001};

  font-weight: 700;
  font-size: 1rem;
`;

export {
  Container,
  Image,
  Description,
  Left,
  Title,
  HostName,
  Deadline,
  HashtagBox,
  Hashtag,
};
