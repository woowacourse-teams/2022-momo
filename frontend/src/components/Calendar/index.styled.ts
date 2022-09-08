import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

type Size = 'medium' | 'large';

const calendarAnimation = keyframes`
  from {
    transform: translate(0, 10px);
  }

  to {
    transform: translate(0, 0);
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;

  ${({ size }: { size: Size }) => `
    transform: scale(${size === 'medium' ? 0.7 : 1});

    font-size: ${size === 'medium' ? '1.8rem' : '1.1rem'};
  `}
`;

const Navigator = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  text-align: center;

  font-size: ${({ size }: { size: Size }) =>
    size === 'medium' ? '2rem' : '1.3rem'};
`;

const Arrow = styled.div`
  display: flex;
  align-items: center;

  max-width: 2rem;

  cursor: pointer;
`;

const LeftArrow = styled(Arrow)`
  justify-content: flex-start;
`;

const RightArrow = styled(Arrow)`
  justify-content: flex-end;
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

  min-width: 1.8rem;
  aspect-ratio: 1;

  border-radius: 50%;
  padding: 0.2rem;

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

export {
  Container,
  Navigator,
  LeftArrow,
  RightArrow,
  Content,
  DayColor,
  PrevNextDate,
  Date,
};
