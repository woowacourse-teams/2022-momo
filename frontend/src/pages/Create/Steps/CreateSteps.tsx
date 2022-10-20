import { NotFound } from 'pages';

import { CreateStateReturnValues } from 'hooks/useCreateState';
import { GroupDetailData } from 'types/data';

import { Step1, Step2, Step3, Step4, Step5 } from './';

interface CreateStepsProp {
  useNameState: CreateStateReturnValues['useNameState'];
  useSelectedCategoryState: CreateStateReturnValues['useSelectedCategoryState'];
  useCapacityState: CreateStateReturnValues['useCapacityState'];
  useDateState: CreateStateReturnValues['useDateState'];
  useScheduleState: CreateStateReturnValues['useScheduleState'];
  useDeadlineState: CreateStateReturnValues['useDeadlineState'];
  useLocationState: CreateStateReturnValues['useLocationState'];
  useDescriptionState: CreateStateReturnValues['useDescriptionState'];
  pressEnterToNext: (e: React.KeyboardEvent<HTMLInputElement>) => void;
  gotoNextPage: () => void;
  getValidateState: (pageIndex: number) => '' | 'invalid';
  duration: GroupDetailData['duration'];
  page: number;
}

function CreateSteps({
  useNameState,
  useSelectedCategoryState,
  useCapacityState,
  useDateState,
  useScheduleState,
  useDeadlineState,
  useLocationState,
  useDescriptionState,
  pressEnterToNext,
  gotoNextPage,
  getValidateState,
  duration,
  page,
}: CreateStepsProp) {
  switch (page) {
    case 1:
      return (
        <Step1
          useNameState={useNameState}
          pressEnterToNext={pressEnterToNext}
          useSelectedCategoryState={useSelectedCategoryState}
          gotoNextPage={gotoNextPage}
        />
      );
    case 2:
      return (
        <Step2
          useDateState={useDateState}
          useDeadlineState={useDeadlineState}
          getValidateState={getValidateState}
          pressEnterToNext={pressEnterToNext}
        />
      );
    case 3:
      return (
        <Step3
          useScheduleState={useScheduleState}
          duration={duration}
          pressEnterToNext={pressEnterToNext}
        />
      );
    case 4:
      return (
        <Step4
          useCapacityState={useCapacityState}
          useLocationState={useLocationState}
          pressEnterToNext={pressEnterToNext}
        />
      );
    case 5: {
      return <Step5 useDescriptionState={useDescriptionState} />;
    }
    default: {
      return <NotFound />;
    }
  }
}

export default CreateSteps;
