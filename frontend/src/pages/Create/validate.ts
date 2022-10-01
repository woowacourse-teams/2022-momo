import { CLIENT_ERROR_MESSAGE } from 'constants/message';
import { GROUP_RULE } from 'constants/rule';
import { CreateGroupData } from 'types/data';
import { resetDateToStartOfDay, resetDateToEndOfDay } from 'utils/date';

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
    return startDate <= endDate && startDate >= resetDateToStartOfDay(today);
  };

const validateDeadlineDate =
  (
    deadline: CreateGroupData['deadline'],
    schedules: CreateGroupData['schedules'],
  ) =>
  () => {
    const parsedDeadline = new Date(deadline);
    const now = new Date();

    if (schedules.length === 0) {
      return parsedDeadline > now;
    }

    schedules.sort((scheduleA, scheduleB) =>
      scheduleA.date.localeCompare(scheduleB.date),
    );

    return (
      parsedDeadline <=
        new Date(`${schedules[0].date}T${schedules[0].startTime}`) &&
      parsedDeadline > now
    );
  };

const validateLocation = (location: CreateGroupData['location']) => () => {
  return location.address.length <= GROUP_RULE.LOCATION.MAX_LENGTH;
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
  schedules,
  deadline,
  location,
  description,
}: CreateGroupData) => {
  const startDateInMidnight = resetDateToStartOfDay(new Date(startDate));
  const endDateInMidnight = resetDateToEndOfDay(new Date(endDate));
  const todayInMidnight = new Date();

  return [
    {
      validator: validateName(name),
      errorMessage: CLIENT_ERROR_MESSAGE.CREATE.NAME,
    },
    {
      validator: validateCategory(selectedCategory),
      errorMessage: CLIENT_ERROR_MESSAGE.CREATE.CATEGORY,
    },
    {
      validator: validateCapacity(capacity),
      errorMessage: CLIENT_ERROR_MESSAGE.CREATE.CAPACITY,
    },
    {
      validator: validateDurationDate(
        startDateInMidnight,
        endDateInMidnight,
        todayInMidnight,
      ),
      errorMessage: CLIENT_ERROR_MESSAGE.CREATE.DURATION,
    },
    {
      validator: validateDeadlineDate(deadline, schedules),
      errorMessage: CLIENT_ERROR_MESSAGE.CREATE.DEADLINE,
    },
    {
      validator: validateLocation(location),
      errorMessage: CLIENT_ERROR_MESSAGE.CREATE.LOCATION,
    },
    {
      validator: validateDescription(description),
      errorMessage: CLIENT_ERROR_MESSAGE.CREATE.DESCRIPTION,
    },
  ];
};

const validateGroupData = (props: CreateGroupData) => {
  const validators = generateValidators(props);

  validators.forEach(({ validator, errorMessage }) => {
    if (!validator()) {
      throw new Error(errorMessage);
    }
  });
};

export default validateGroupData;
