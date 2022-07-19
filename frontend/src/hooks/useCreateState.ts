import { useState } from 'react';

import { CategoryType, Group } from 'types/data';

const useCreateState = () => {
  const [name, setName] = useState('');
  const [selectedCategory, setSelectedCategory] = useState<CategoryType>({
    id: -1,
    name: '',
  });
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [deadline, setDeadline] = useState('');
  const [location, setLocation] = useState('');
  const [description, setDescription] = useState('');

  const changeName = (newName: Group['name']) => {
    setName(newName);
  };

  const changeSelectedCategory = (newSelectedCategory: CategoryType) => {
    setSelectedCategory(newSelectedCategory);
  };

  const changeStartDate = (newStartDate: string) => {
    setStartDate(newStartDate);
  };

  const changeEndDate = (newEndDate: string) => {
    setEndDate(newEndDate);
  };

  const changeDeadline = (newDeadline: string) => {
    setDeadline(newDeadline);
  };

  const changeLocation = (newLocation: string) => {
    setLocation(newLocation);
  };

  const changeDescription = (newDescription: string) => {
    setDescription(newDescription);
  };

  return {
    useNameState: () => ({
      name,
      setName: changeName,
    }),
    useSelectedCategoryState: () => ({
      selectedCategory,
      setSelectedCategory: changeSelectedCategory,
    }),
    useDateState: () => ({
      startDate,
      setStartDate: changeStartDate,
      endDate,
      setEndDate: changeEndDate,
    }),
    useDeadlineState: () => ({
      deadline,
      setDeadline: changeDeadline,
    }),
    useLocationState: () => ({
      location,
      setLocation: changeLocation,
    }),
    useDescriptionState: () => ({
      description,
      setDescription: changeDescription,
    }),
    getGroupState: () => ({
      name,
      selectedCategory,
      startDate,
      endDate,
      deadline,
      location,
      description,
    }),
  };
};

export default useCreateState;
