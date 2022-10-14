import styled from '@emotion/styled';

const Button = styled.button`
  height: 3rem;

  border-radius: 50px;

  font-size: 1.2rem;
  font-weight: 700;
`;

const HostButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const HostButton = styled(Button)`
  width: 47%;
`;

const EarlyClosedButton = styled(HostButton)`
  ${({ theme: { colors } }) => `
    border: 0.5px solid ${colors.red003};

    background: ${colors.white001};
    color: ${colors.red003};
  `}
`;

const DeleteButton = styled(HostButton)`
  ${({ theme: { colors } }) => `
    background: linear-gradient(
      180deg,
      ${colors.red003}99 0%,
      ${colors.red003} 20%
    );
    color: ${colors.white001};
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
