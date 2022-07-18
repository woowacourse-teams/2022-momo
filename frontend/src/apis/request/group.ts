import axios from 'apis/axios';
import { ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { CreateGroupData, Group } from 'types/data';

const requestCreateGroup = async ({
  name,
  selectedCategory,
  startDate,
  endDate,
  deadline,
  location,
  description,
}: CreateGroupData) => {
  const data = {
    name,
    categoryId: selectedCategory.id,
    duration: {
      start: startDate,
      end: endDate,
    },
    // TODO: 달력 입력에 따라 스케쥴 시간 바꾸기
    schedules: [
      {
        date: startDate,
        startTime: '10:00',
        endTime: '18:00',
      },
      {
        date: endDate,
        startTime: '10:00',
        endTime: '18:00',
      },
    ],
    deadline,
    location,
    description,
  };

  axios
    .post(API_PATH.GROUP, data)
    .then(res => {
      // 요청: body에 생성된 모임의 id를 줘! (줄 예정)
      return res;
    })
    .catch(() => {
      throw new Error(ERROR_MESSAGE.CREATE.FAILURE_REQUEST);
    });
};

const getGroups = (): Promise<Group[]> => {
  return axios.get(`${API_PATH.GROUP}`).then(response => response.data);
};

export { requestCreateGroup, getGroups };
