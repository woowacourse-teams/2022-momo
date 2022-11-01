import { GroupDetailData } from 'types/data';

const prevLocationProvider = {
  get: (): string => {
    return sessionStorage.getItem('location') ?? '';
  },
  set: (location: string) => {
    sessionStorage.setItem('location', location);
  },
  remove: (): void => {
    sessionStorage.removeItem('location');
  },
};

const processLocation = (location: GroupDetailData['location']) => {
  const processedLocation = `${
    location.buildingName ? location.buildingName : location.address ?? ''
  } ${location.detail}`;

  return processedLocation.trim() === '' ? '(미정)' : processedLocation;
};

export { prevLocationProvider, processLocation };
