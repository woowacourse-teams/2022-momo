import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  min-width: max-content;

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 10px;

  padding-top: 2rem;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;

  min-height: 32px;

  padding: 0 2rem;
`;

const Text = styled.span`
  color: ${({ theme: { colors } }) => colors.black002};

  margin-left: 1rem;

  font-weight: 700;
`;

const JoinButton = styled.button`
  height: 4rem;

  background: linear-gradient(
    180deg,
    ${({ theme: { colors } }) => colors.blue001}99 0%,
    ${({ theme: { colors } }) => colors.blue001} 20%
  );
  color: ${({ theme: { colors } }) => colors.white001};

  border-radius: 0 0 10px 10px;

  margin-top: 1rem;

  font-size: 1.3rem;
  font-weight: 700;
`;

export { Container, Wrapper, Text, JoinButton };
