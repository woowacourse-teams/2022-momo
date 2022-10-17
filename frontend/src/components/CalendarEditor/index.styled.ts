import styled from '@emotion/styled';

import { Input as BasicInput } from 'pages/Create/Steps/@shared/styled';

const Content = styled.div`
  display: flex;
  gap: 2rem;

  max-width: 56rem;
  height: fit-content;

  ${({
    theme: {
      breakpoints: { md },
    },
  }) =>
    `
      @media only screen and (max-width: ${md}px) {
        flex-direction: column;
        align-items: center;
        gap: 0;
      }
    `}
`;

const Left = styled.div`
  max-width: 20rem;
  height: 25rem;
`;

const Right = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 2rem;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;

  min-width: max-content;

  padding-top: 2rem;
`;

const TimeContainer = styled.span`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1rem;

  width: 100%;
`;

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: fit-content;
`;

const Text = styled.span`
  white-space: nowrap;

  color: ${({ theme: { colors } }) => colors.black002};

  margin-left: 1rem;

  font-weight: 700;
`;

const DailyButton = styled.button`
  width: 2.5rem;
  height: 2.5rem;

  border-radius: 50%;

  ${({ theme: { colors } }) => `
    background: ${colors.white001};
    color: ${colors.gray002};

    border: 1px solid ${colors.gray002};

    &.active {
      color: ${colors.yellow001};

      border: 1px solid ${colors.yellow001};
    }
  `}
`;

const DayBox = styled.div`
  display: flex;
  justify-content: space-between;

  span {
    cursor: pointer;
  }

  ${({ theme: { colors } }) => `
    .sun {
      color: ${colors.red001};
    }

    .sat {
      color: ${colors.blue001};
    }
  `}
`;

const InputWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;

  ${({
    theme: {
      breakpoints: { md },
    },
  }) => `
        @media only screen and (max-width: ${md}px) {
          display: grid;
          grid-template-columns: 3fr 1fr;
          place-items: center;
        }
      `}
`;

const Input = styled(BasicInput)`
  width: 9rem;
  height: 3rem;

  font-size: 1rem;
`;

const ControlButton = styled.button`
  width: 100%;
  max-width: 23rem;
  height: 3rem;

  background: ${({ theme: { colors } }) => colors.white001};

  border-radius: 4px;
`;

const AddButton = styled(ControlButton)`
  ${({ theme: { colors } }) => `
    color: ${colors.blue001};

    border: 1px solid ${colors.blue001};
  `}
`;

const DeleteButton = styled(ControlButton)`
  ${({ theme: { colors } }) => `
    color: ${colors.red001};

    border: 1px solid ${colors.red001};
  `}
`;

export {
  Content,
  Left,
  Right,
  Container,
  TimeContainer,
  Wrapper,
  Text,
  DailyButton,
  DayBox,
  InputWrapper,
  Input,
  AddButton,
  DeleteButton,
};
