import styled from '@emotion/styled';

const HostButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Button = styled.button`
  height: 4rem;

  border-radius: 0 0 10px 10px;

  font-size: 1.3rem;
  font-weight: 700;
`;

const HostButton = styled(Button)`
  width: 50%;
`;

const EarlyClosedButton = styled(HostButton)`
  border-radius: 0 0 0 10px;

  ${({ theme: { colors } }) => `
    border-top: 1px solid ${colors.gray002};

    background: ${colors.white001};
    color: ${colors.red003};

    &:hover {
      background: ${colors.gray005};
    }
  `}
`;

const DeleteButton = styled(HostButton)`
  border-radius: 0 0 10px 0;

  ${({ theme: { colors } }) => `
    background: linear-gradient(
      180deg,
      ${colors.red003}99 0%,
      ${colors.red003} 20%
    );
    color: ${colors.white001};

    &:hover {
      background: linear-gradient(
        180deg,
        ${colors.red002}99 0%,
        ${colors.red002} 20%
      );
    }
  `}
`;

const JoinButton = styled(Button)`
  ${({ theme: { colors } }) => `
    background: linear-gradient(
      180deg,
      ${colors.blue002}99 0%,
      ${colors.blue002} 20%
    );
    color: ${colors.white001};

    &:hover {
      background: linear-gradient(
        180deg,
        ${colors.blue001}99 0%,
        ${colors.blue001} 20%
      );
    }
  `}
`;

const ExitButton = styled(Button)`
  ${({ theme: { colors } }) => `
    background: linear-gradient(
      180deg,
      ${colors.green002}99 0%,
      ${colors.green002} 20%
    );
    color: ${colors.white001};

    &:hover {
      background: linear-gradient(
        180deg,
        ${colors.green001}99 0%,
        ${colors.green001} 20%
      );
    }
  `}
`;

const DisableButton = styled(Button)`
  cursor: not-allowed;

  ${({ theme: { colors } }) => `
    background: linear-gradient(
      180deg,
      ${colors.gray002}99 0%,
      ${colors.gray002} 20%
    );
    color: ${colors.white001};
  `}
`;

export {
  HostButtonContainer,
  EarlyClosedButton,
  DeleteButton,
  JoinButton,
  ExitButton,
  DisableButton,
};
