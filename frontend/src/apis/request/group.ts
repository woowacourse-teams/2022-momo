import axios from 'apis/axios';
import { ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { GROUP_RULE } from 'constants/rule';
import {
  CreateGroupData,
  GroupDetailData,
  GroupParticipants,
  GroupList,
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
    .post<{ groupId: GroupDetailData['id'] }>(API_PATH.GROUP, data, {
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

const getGroups = (pageNumber: number) => () => {
  return axios
    .get<GroupList>(`${API_PATH.GROUP}?page=${pageNumber}`)
    .then(response => response.data);
};

const getGroupDetail = (id: GroupDetailData['id']) => {
  return axios
    .get<GroupDetailData>(`${API_PATH.GROUP}/${id}`)
    .then(response => response.data);
};

const deleteGroup = (id: GroupDetailData['id']) => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios.delete(`${API_PATH.GROUP}/${id}`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

const getGroupParticipants = (id: GroupDetailData['id']) => {
  return axios
    .get<GroupParticipants>(`${API_PATH.GROUP}/${id}${API_PATH.PARTICIPANTS}`)
    .then(response => response.data);
};

const joinGroup = (id: GroupDetailData['id']) => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios.post(
    `${API_PATH.GROUP}/${id}${API_PATH.PARTICIPANTS}`,
    {},
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );
};

const exitGroup = (id: GroupDetailData['id']) => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios.delete(`${API_PATH.GROUP}/${id}${API_PATH.PARTICIPANTS}`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
};

export {
  requestCreateGroup,
  getGroups,
  getGroupDetail,
  deleteGroup,
  getGroupParticipants,
  joinGroup,
  exitGroup,
};
