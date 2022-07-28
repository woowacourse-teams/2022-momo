import axios from 'apis/axios';
import { ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { GROUP_RULE } from 'constants/rule';
import {
  CreateGroupData,
  DetailData,
  Group,
  GroupParticipants,
} from 'types/data';

const requestCreateGroup = async ({
  name,
  selectedCategory,
  capacity,
  startDate,
  endDate,
  deadline,
  location,
  description,
}: CreateGroupData) => {
  const data = {
    name,
    categoryId: selectedCategory.id,
    capacity: capacity || GROUP_RULE.CAPACITY.MAX,
    duration: {
      start: startDate,
      end: endDate,
    },
    // TODO: 달력 입력에 따라 스케줄 시간 바꾸기
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

  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios
    .post(API_PATH.GROUP, data, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then(response => {
      return response.data.groupId;
    })
    .catch(() => {
      throw new Error(ERROR_MESSAGE.CREATE.FAILURE_REQUEST);
    });
};

const getGroups = (): Promise<Group[]> => {
  return axios.get(API_PATH.GROUP).then(response => response.data);
};

const getGroupDetail = (id: DetailData['id']): Promise<DetailData> => {
  return axios.get(`${API_PATH.GROUP}/${id}`).then(response => response.data);
};

const deleteGroup = (id: DetailData['id']): Promise<void> => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios.delete(`${API_PATH.GROUP}/${id}`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

const getGroupParticipants = (
  id: DetailData['id'],
): Promise<GroupParticipants> => {
  return axios
    .get(`${API_PATH.GROUP}/${id}${API_PATH.PARTICIPANTS}`)
    .then(response => response.data);
};

export {
  requestCreateGroup,
  getGroups,
  getGroupDetail,
  deleteGroup,
  getGroupParticipants,
};
