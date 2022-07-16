import axios from 'apis/axios';
import { API_PATH } from 'constants/path';
import { CategoryType } from 'types/data';

const getCategory = (): Promise<CategoryType[]> => {
  return axios.get(`${API_PATH.CATEGORY}`).then(response => response.data);
};

export { getCategory };
