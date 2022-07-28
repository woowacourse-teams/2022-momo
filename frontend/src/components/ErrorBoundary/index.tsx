import { Component, ReactNode } from 'react';

import * as S from './index.styled';

interface ErrorBoundaryProps {
  children: ReactNode;
  isError?: boolean;
}

class ErrorBoundary extends Component<ErrorBoundaryProps> {
  state = { isError: false };

  static getDerivedStateFromError(
    _: Error,
  ): Pick<ErrorBoundaryProps, 'isError'> {
    return { isError: true };
  }

  componentDidCatch(error: Error) {
    this.setState({
      error,
    });
  }

  render() {
    if (this.state.isError) {
      return <S.Container>에러가 발생하였습니다</S.Container>;
    }

    return <>{this.props.children}</>;
  }
}

export default ErrorBoundary;
