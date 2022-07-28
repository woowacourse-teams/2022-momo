import styled from '@emotion/styled';

import { Input as BasicInput } from '../@shared/styled';

const Content = styled.div`
  display: flex;
  gap: 4rem;

  max-width: 43.75rem;
`;

const Calendar = styled.div`
  width: 10rem;
  height: 10rem;

  background: ${({ theme: { colors } }) => colors.gray003};
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
    color: ${({ theme: { colors } }) => colors.blue002};
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
  Calendar,
  Right,
  DailyButton,
  DayBox,
  InputWrapper,
  Input,
  AddButton,
};
