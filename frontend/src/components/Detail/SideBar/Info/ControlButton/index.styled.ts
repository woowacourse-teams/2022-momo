import styled from '@emotion/styled';

const HostButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Button = styled.button`
  height: 4rem;

  border-radius: 0 0 10px 10px;

  margin-top: 1rem;

  font-size: 1.3rem;
  font-weight: 700;
`;

const HostButton = styled(Button)`
  width: 50%;
`;

const EarlyClosedButton = styled(HostButton)`
  border-top: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 0 0 0 10px;

  background: ${({ theme: { colors } }) => colors.white001};
  color: ${({ theme: { colors } }) => colors.red003};

  &:hover {
    background: ${({ theme: { colors } }) => colors.gray005};
  }
`;

const DeleteButton = styled(HostButton)`
  border-radius: 0 0 10px 0;

  background: linear-gradient(
    180deg,
    ${({ theme: { colors } }) => colors.red003}99 0%,
    ${({ theme: { colors } }) => colors.red003} 20%
  );
  color: ${({ theme: { colors } }) => colors.white001};

  &:hover {
    background: linear-gradient(
      180deg,
      ${({ theme: { colors } }) => colors.red002}99 0%,
      ${({ theme: { colors } }) => colors.red002} 20%
    );
  }
`;

const JoinButton = styled(Button)`
  background: linear-gradient(
    180deg,
    ${({ theme: { colors } }) => colors.blue002}99 0%,
    ${({ theme: { colors } }) => colors.blue002} 20%
  );
  color: ${({ theme: { colors } }) => colors.white001};

  &:hover {
    background: linear-gradient(
      180deg,
      ${({ theme: { colors } }) => colors.blue001}99 0%,
      ${({ theme: { colors } }) => colors.blue001} 20%
    );
  }
`;

const ExitButton = styled(Button)`
  background: linear-gradient(
    180deg,
    ${({ theme: { colors } }) => colors.green002}99 0%,
    ${({ theme: { colors } }) => colors.green002} 20%
  );
  color: ${({ theme: { colors } }) => colors.white001};

  &:hover {
    background: linear-gradient(
      180deg,
      ${({ theme: { colors } }) => colors.green001}99 0%,
      ${({ theme: { colors } }) => colors.green001} 20%
    );
  }
`;

const DisableButton = styled(Button)`
  background: linear-gradient(
    180deg,
    ${({ theme: { colors } }) => colors.gray002}99 0%,
    ${({ theme: { colors } }) => colors.gray002} 20%
  );
  color: ${({ theme: { colors } }) => colors.white001};

  cursor: not-allowed;
`;

export {
  HostButtonContainer,
  EarlyClosedButton,
  DeleteButton,
  JoinButton,
  ExitButton,
  DisableButton,
};
