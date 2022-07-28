import { ERROR_MESSAGE } from 'constants/message';
import { GROUP_RULE } from 'constants/rule';
import { CreateGroupData } from 'types/data';
import { resetDateToMidnight } from 'utils/date';
import PageError from 'utils/PageError';

const validateName = (name: CreateGroupData['name']) => () => {
  return (
    name.length >= GROUP_RULE.NAME.MIN_LENGTH &&
    name.length <= GROUP_RULE.NAME.MAX_LENGTH
  );
};

const validateCategory =
  (category: CreateGroupData['selectedCategory']) => () => {
    return !!category.id;
  };

const validateCapacity = (capacity: CreateGroupData['capacity']) => () => {
  return (
    !capacity ||
    (capacity >= GROUP_RULE.CAPACITY.MIN && capacity <= GROUP_RULE.CAPACITY.MAX)
  );
};

const validateDurationDate =
  (startDate: Date, endDate: Date, today: Date) => () => {
    return startDate <= endDate && startDate >= today;
  };

const validateDeadlineDate =
  (
    deadline: CreateGroupData['deadline'],
    startDate: CreateGroupData['startDate'],
  ) =>
  () => {
    const parsedDeadline = new Date(deadline);
    const parsedStartDate = new Date(startDate);
    const now = new Date();

    return parsedDeadline < parsedStartDate && parsedDeadline >= now;
  };

const validateLocation = (location: CreateGroupData['location']) => () => {
  return location.length <= GROUP_RULE.LOCATION.MAX_LENGTH;
};

const validateDescription =
  (description: CreateGroupData['description']) => () => {
    return description.length <= GROUP_RULE.DESCRIPTION.MAX_LENGTH;
  };

const generateValidators = ({
  name,
  selectedCategory,
  capacity,
  startDate,
  endDate,
  deadline,
  location,
  description,
}: CreateGroupData) => {
  const startDateInMidnight = resetDateToMidnight(new Date(startDate));
  const endDateInMidnight = resetDateToMidnight(new Date(endDate));
  const todayInMidnight = resetDateToMidnight(new Date());

  return [
    {
      validator: validateName(name),
      errorMessage: ERROR_MESSAGE.CREATE.NAME,
      targetPageNumber: 1,
    },
    {
      validator: validateCategory(selectedCategory),
      errorMessage: ERROR_MESSAGE.CREATE.CATEGORY,
      targetPageNumber: 2,
    },
    {
      validator: validateCapacity(capacity),
      errorMessage: ERROR_MESSAGE.CREATE.CAPACITY,
      targetPageNumber: 3,
    },
    {
      validator: validateDurationDate(
        startDateInMidnight,
        endDateInMidnight,
        todayInMidnight,
      ),
      errorMessage: ERROR_MESSAGE.CREATE.DURATION,
      targetPageNumber: 4,
    },
    // TODO: 4번째는 달력
    {
      validator: validateDeadlineDate(deadline, startDate),
      errorMessage: ERROR_MESSAGE.CREATE.DEADLINE,
      targetPageNumber: 6,
    },
    {
      validator: validateLocation(location),
      errorMessage: ERROR_MESSAGE.CREATE.LOCATION,
      targetPageNumber: 7,
    },
    {
      validator: validateDescription(description),
      errorMessage: ERROR_MESSAGE.CREATE.DESCRIPTION,
      targetPageNumber: 8,
    },
  ];
};

const validateGroupData = (props: CreateGroupData) => {
  const validators = generateValidators(props);

  validators.forEach(({ validator, errorMessage, targetPageNumber }) => {
    if (!validator()) {
      throw new PageError(errorMessage, targetPageNumber);
    }
  });
};

export default validateGroupData;
