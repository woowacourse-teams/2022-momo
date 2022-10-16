import { Component } from 'react';

import { Error as ErrorAnimation } from 'components/Animation';

import * as S from './index.styled';

interface ErrorBoundaryProps {
  children: React.ReactNode;
  isError?: boolean;
  fallbackUI?: React.ReactNode;
}

class ErrorBoundary extends Component<ErrorBoundaryProps> {
  state = {
    isError: false,
  };

  static getDerivedStateFromError(
    _: Error,
  ): Pick<ErrorBoundaryProps, 'isError'> {
    return {
      isError: true,
    };
  }

  componentDidCatch(error: Error) {
    this.setState({
      error,
    });
  }

  render() {
    if (!this.state.isError) {
      return <>{this.props.children}</>;
    }

    return (
      <>
        {this.props.fallbackUI || (
          <S.Container>
            <ErrorAnimation />
            <S.Description>
              <S.Title>에러가 발생했어요 😥</S.Title>
              <p>
                잠시 후 새로고침을 해보시고, 문제가 지속되면 관리자에게 문의
                부탁드려요.
              </p>
            </S.Description>
          </S.Container>
        )}
      </>
    );
  }
}

export default ErrorBoundary;
