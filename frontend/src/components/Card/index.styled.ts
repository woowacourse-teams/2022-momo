import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 0.5rem;

  width: 100%;
  height: 7rem;

  border-top: 1px solid ${({ theme: { colors } }) => colors.gray002};

  transition: transform 0.2s;

  filter: ${({ finished }: { finished: boolean }) =>
    finished ? `contrast(50%) grayscale(100%)` : ''};

  cursor: pointer;

  &:hover {
    transform: scale3d(1.02, 1.02, 1.02);
  }
`;

const Image = styled.div`
  width: 25%;
  height: 70%;

  border-radius: 20px;

  background: url(${({ imgSrc }: { imgSrc: string }) => imgSrc});
  background-size: cover;
  background-position: center;

  margin: auto;
  margin-left: 0.5rem;
`;

const Description = styled.div`
  display: flex;
  justify-content: space-between;

  width: 70%;
  line-height: 1.4em;

  padding: 1rem 3%;
`;

const Left = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  width: 60%;
  height: 100%;
`;

const Right = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;

  width: 40%;
  height: 100%;
`;

const Title = styled.div`
  display: -webkit-box;

  overflow: hidden;
  text-overflow: ellipsis;
  word-wrap: break-word;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;

  font-weight: 700;
  font-size: 1.2rem;
`;

const HostName = styled.div`
  display: -webkit-box;

  overflow: hidden;
  text-overflow: ellipsis;
  word-wrap: break-word;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;

  color: ${({ theme: { colors } }) => colors.gray001};

  font-weight: 100;
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
  min-width: 40%;
  max-width: 90%;

  color: ${({ theme: { colors } }) => colors.red003};

  font-weight: 700;
  font-size: 1rem;
`;

const Capacity = styled.div`
  font-size: 1rem;

  ${({ theme: { colors } }) => `
    color: ${colors.gray001};

    span {
      color: ${colors.blue002};
    }
  `}
`;

export {
  Container,
  Image,
  Description,
  Left,
  Right,
  Title,
  HostName,
  HashtagBox,
  Hashtag,
  Deadline,
  Capacity,
};
