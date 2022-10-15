import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

import { preventUserSelect } from 'styles/common';

const calendarAnimation = keyframes`
  from {
    transform: translate3d(0, 0.5rem, 0);
  }

  to {
    transform: translate3d(0, 0, 0);
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  font-size: 1rem;

  ${preventUserSelect}
`;

const Navigator = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  text-align: center;

  font-size: 1.2rem;
`;

const Arrow = styled.div<{ direction: 'left' | 'right' }>`
  display: flex;
  justify-content: ${({ direction }) =>
    direction === 'left' ? 'flex-start' : 'flex-end'};
  align-items: center;

  max-width: 2rem;

  cursor: pointer;
`;

const Content = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  place-items: center;
  text-align: center;
  gap: 1rem;

  animation: ${calendarAnimation} 0.2s;
`;

const DayColor = styled.div`
  ${({ theme: { colors } }) => `
    &.sat {
      color: ${colors.blue001};
    }

    &.sun {
      color: ${colors.red001};
    }
  `}
`;

const PrevNextDate = styled.div`
  ${({ theme: { colors } }) => `
    color: ${colors.gray001};

    &.disabled {
      color: ${colors.gray003};
    }
  `}
`;

const Date = styled(DayColor)`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 1rem;
  aspect-ratio: 1 / 1;

  border-radius: 50%;
  padding: 0.1rem;

  cursor: pointer;

  ${({ theme: { colors } }) => `
    &.today {
      background: ${colors.red003};
      color: ${colors.white001};
    }

    &:hover,
    &.selected {
      background: ${colors.yellow002};
    }

    &.disabled {
      color: ${colors.gray005};

      pointer-events: none;
    }
  `}
`;

export { Container, Navigator, Arrow, Content, DayColor, PrevNextDate, Date };
