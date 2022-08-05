import styled from '@emotion/styled';

import { Input as BasicInput } from '../@shared/styled';

const Content = styled.div`
  display: flex;
  gap: 4rem;

  max-width: 55rem;
  height: 27rem;
`;

const Left = styled.div`
  max-width: 22rem;
`;

const Right = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 2rem;
`;

const DailyButton = styled.button`
  width: 2.5rem;
  height: 2.5rem;

  background: ${({ theme: { colors } }) => colors.white001};
  color: ${({ theme: { colors } }) => colors.gray002};

  border: 1px solid ${({ theme: { colors } }) => colors.gray002};
  border-radius: 50%;

  &.active {
    color: ${({ theme: { colors } }) => colors.yellow001};

    border: 1px solid ${({ theme: { colors } }) => colors.yellow001};
  }
`;

const DayBox = styled.div`
  display: flex;
  justify-content: space-between;

  span {
    cursor: pointer;
  }

  .sun {
    color: ${({ theme: { colors } }) => colors.red001};
  }

  .sat {
    color: ${({ theme: { colors } }) => colors.blue001};
  }
`;

const InputWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
`;

const Input = styled(BasicInput)`
  width: 10rem;
  height: 3rem;
`;

const AddButton = styled.button`
  width: fit-content;

  background: ${({ theme: { colors } }) => colors.white001};
  color: ${({ theme: { colors } }) => colors.blue001};

  font-size: 1.1rem;
`;

export {
  Content,
  Left,
  Right,
  DailyButton,
  DayBox,
  InputWrapper,
  Input,
  AddButton,
};
