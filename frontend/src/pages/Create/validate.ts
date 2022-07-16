import { ERROR_MESSAGE } from 'constants/message';
import { GROUP_RULE } from 'constants/rule';
import { DetailData, CategoryType, DurationDate } from 'types/data';
import { resetDateToMidnight } from 'utils/date';
import PageError from 'utils/PageError';

type ValidatorProps = Pick<
  DetailData,
  'name' | 'deadline' | 'location' | 'description'
> &
  DurationDate & {
    selectedCategory: CategoryType;
  };

const validateName = (name: ValidatorProps['name']) => () => {
  return (
    name.length >= GROUP_RULE.NAME.MIN_LENGTH &&
    name.length <= GROUP_RULE.NAME.MAX_LENGTH
  );
};

const validateCategory =
  (category: ValidatorProps['selectedCategory']) => () => {
    return !!category.id;
  };

const validateDurationDate =
  (startDate: Date, endDate: Date, today: Date) => () => {
    return startDate <= endDate && startDate >= today;
  };

const validateDeadlineDate = (deadline: string, startDate: string) => () => {
  const parsedDeadline = new Date(deadline);
  const parsedStartDate = new Date(startDate);
  const now = new Date();

  return parsedDeadline >= parsedStartDate && parsedDeadline >= now;
};

const validateLocation = (location: ValidatorProps['location']) => () => {
  return location.length <= GROUP_RULE.LOCATION.MAX_LENGTH;
};

const validateDescription =
  (description: ValidatorProps['description']) => () => {
    return description.length <= GROUP_RULE.DESCRIPTION.MAX_LENGTH;
  };

const generateValidators = ({
  name,
  selectedCategory,
  startDate,
  endDate,
  deadline,
  location,
  description,
}: ValidatorProps) => {
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
      validator: validateDurationDate(
        startDateInMidnight,
        endDateInMidnight,
        todayInMidnight,
      ),
      errorMessage: ERROR_MESSAGE.CREATE.DURATION,
      targetPageNumber: 3,
    },
    // TODO: 4번째는 달력
    {
      validator: validateDeadlineDate(deadline, startDate),
      errorMessage: ERROR_MESSAGE.CREATE.DEADLINE,
      targetPageNumber: 5,
    },
    {
      validator: validateLocation(location),
      errorMessage: ERROR_MESSAGE.CREATE.LOCATION,
      targetPageNumber: 6,
    },
    {
      validator: validateDescription(description),
      errorMessage: ERROR_MESSAGE.CREATE.DESCRIPTION,
      targetPageNumber: 7,
    },
  ];
};

const validator = (props: ValidatorProps) => {
  const validators = generateValidators(props);

  validators.forEach(({ validator, errorMessage, targetPageNumber }) => {
    if (!validator()) {
      throw new PageError(errorMessage, targetPageNumber);
    }
  });
};

export default validator;
