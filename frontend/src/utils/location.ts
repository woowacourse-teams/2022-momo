import { GroupDetailData } from 'types/data';

const processLocation = (location: GroupDetailData['location']) => {
  const processedLocation = `${
    location.buildingName ? location.buildingName : location.address ?? ''
  } ${location.detail}`;

  return processedLocation.trim() === '' ? '(미정)' : processedLocation;
};

export { processLocation };
