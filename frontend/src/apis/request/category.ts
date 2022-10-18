import { axios } from 'apis/axios';
import { API_PATH } from 'constants/path';
import { CategoryType } from 'types/data';

const requestCategory = () => {
  return axios
    .get<CategoryType[]>(API_PATH.CATEGORY)
    .then(response => response.data);
};

export { requestCategory };
