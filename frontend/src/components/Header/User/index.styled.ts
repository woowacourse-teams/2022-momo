import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const showDropdown = keyframes`
  from {
    transform: translate3d(-7rem, 0, 0);

    opacity: 0;
  }

  to {
    transform: translate3d(-7rem, 3rem, 0);

    opacity: 1;
  }
`;

const hideDropdown = keyframes`
  from {
    transform: translate3d(-7rem, 3rem, 0);

    opacity: 1;
  }

  to {
    transform: translate3d(-7rem, 0, 0);

    opacity: 0;
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

const Profile = styled.div<{ width: string }>`
  display: flex;
  justify-content: center;
  align-items: center;

  aspect-ratio: 1 / 1;

  border-radius: 50%;

  font-size: 16px;

  ${({ theme: { colors }, width }) => `
    width: ${width};

    background: ${colors.gray004};
  `}
`;

const ToggleButton = styled.button`
  width: fit-content;
  height: fit-content;

  background: none;
  border-radius: 50%;
`;

const Dropdown = styled.div<{ animationTime: number }>`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  position: absolute;
  transform: translate3d(-7rem, 3rem, 0);

  width: 10rem;

  border-radius: 4px;

  * {
    cursor: pointer;
  }

  ${({ theme: { colors } }) => `
    background: ${colors.white001};
    filter: drop-shadow(0 0 1.5px ${colors.gray001});

    div:not(:last-child) {
      border-bottom: 1px solid ${colors.gray003};
    }
  `}

  ${({ animationTime }) => css`
    animation: ${showDropdown} ${animationTime}ms;

    &.close {
      animation: ${hideDropdown} ${animationTime}ms;
    }
  `};
`;

const User = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1rem;

  width: 100%;
  height: 10rem;
`;

const Option = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 3rem;
`;

const SvgWrapper = styled.div`
  svg {
    display: block;
  }
`;

export { Container, Profile, ToggleButton, Dropdown, User, Option, SvgWrapper };
