import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;

  width: 21.875rem;
  height: 18.75rem;

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 20px;

  filter: ${({ finished }: { finished: boolean }) =>
    finished ? `contrast(50%) grayscale(100%)` : ''};

  cursor: pointer;

  &:hover {
    transform: scale(1.02);
  }
`;

const Image = styled.div`
  width: 100%;
  height: 170%;

  border-radius: 20px 20px 0 0;

  background: url(${({ imgSrc }: { imgSrc: string }) => imgSrc});
  background-size: cover;
`;

const Description = styled.div`
  display: flex;
  justify-content: space-between;

  height: 100%;
  line-height: 1.4em;

  padding: 1rem;
`;

const Left = styled.div`
  display: flex;
  flex-direction: column;

  width: 50%;
`;

const Right = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;

  width: 50%;
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
  color: ${({ theme: { colors } }) => colors.gray001};

  font-weight: 500;
  font-size: 1.1rem;
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

  color: ${({ theme: { colors } }) => colors.red003};

  font-weight: 700;
  font-size: 1rem;
`;

const Capacity = styled.div`
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
