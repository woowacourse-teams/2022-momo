import CategoryFallback from './Fallback/Category';
import LocationFallback from './Fallback/Location';

import ErrorBoundary from '.';

const story = {
  title: 'Component/ErrorBoundary',
  component: ErrorBoundary,
  parameters: {
    layout: 'fullscreen',
  },
};

export default story;

function ErrorComponent() {
  throw new Error();

  // eslint-disable-next-line no-unreachable
  return <div>component</div>;
}

function DefaultTemplate() {
  return (
    <ErrorBoundary>
      <ErrorComponent />
    </ErrorBoundary>
  );
}

export const Default = DefaultTemplate.bind({});

function CategoryTemplate() {
  return (
    <div style={{ width: '90%' }}>
      <ErrorBoundary fallbackUI={<CategoryFallback />}>
        <ErrorComponent />
      </ErrorBoundary>
    </div>
  );
}

export const Category = CategoryTemplate.bind({});

function LocationTemplate() {
  return (
    <div style={{ width: '90%', maxWidth: '25rem' }}>
      <ErrorBoundary fallbackUI={<LocationFallback />}>
        <ErrorComponent />
      </ErrorBoundary>
    </div>
  );
}

export const Location = LocationTemplate.bind({});
