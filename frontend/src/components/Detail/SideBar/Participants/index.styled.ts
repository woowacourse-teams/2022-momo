import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;

  min-width: max-content;

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 10px;

  padding: 2rem 0;
`;

const Header = styled.p`
  text-align: center;

  color: ${({ theme: { colors } }) => colors.black002};

  font-size: 1.6rem;
  font-weight: 700;
`;

const Summary = styled.div`
  text-align: center;

  color: ${({ theme: { colors } }) => colors.gray001};

  font-size: 1rem;

  span {
    color: ${({ theme: { colors } }) => colors.blue002};
  }
`;

const Box = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;

  min-height: 2rem;

  padding: 0 2rem;
`;

const Text = styled.span`
  color: ${({ theme: { colors } }) => colors.black002};

  margin-left: 1rem;

  font-weight: 600;
`;

const HostText = styled(Text)`
  color: ${({ theme: { colors } }) => colors.yellow001};
`;

export { Container, Header, Summary, Box, Wrapper, Text, HostText };
