import axios from 'apis/axios';
import { ERROR_MESSAGE } from 'constants/message';
import { API_PATH } from 'constants/path';
import { GROUP_RULE } from 'constants/rule';
import {
  CreateGroupData,
  GroupDetailData,
  GroupParticipants,
  GroupList,
  GroupSummary,
} from 'types/data';

const requestCreateGroup = ({
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
  const data = {
    name,
    categoryId: selectedCategory.id,
    capacity: capacity || GROUP_RULE.CAPACITY.MAX,
    duration: {
      start: startDate,
      end: endDate,
    },
    schedules,
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

const requestEditGroup = (
  {
    name,
    selectedCategory,
    capacity,
    startDate,
    endDate,
    schedules,
    deadline,
    location,
    description,
  }: CreateGroupData,
  id: GroupDetailData['id'],
) => {
  const data = {
    name,
    categoryId: selectedCategory.id,
    capacity: capacity || GROUP_RULE.CAPACITY.MAX,
    duration: {
      start: startDate,
      end: endDate,
    },
    schedules,
    deadline,
    location,
    description,
  };

  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios
    .put<{ groupId: GroupDetailData['id'] }>(`${API_PATH.GROUP}/${id}`, data, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then(response => {
      return response.data.groupId;
    })
    .catch(() => {
      throw new Error(ERROR_MESSAGE.GROUP.FAILURE_EDIT_GROUP);
    });
};

const getJoinedGroups = () => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios
    .get<GroupSummary[]>(API_PATH.JOINED_GROUP, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .then(response => response.data);
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

const requestCloseGroup = (id: GroupDetailData['id']) => {
  const accessToken = sessionStorage.getItem('accessToken') ?? '';

  return axios.post(
    `${API_PATH.GROUP}/${id}${API_PATH.CLOSE}`,
    {},
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );
};

export {
  requestCreateGroup,
  requestEditGroup,
  getJoinedGroups,
  getGroups,
  getGroupDetail,
  deleteGroup,
  getGroupParticipants,
  joinGroup,
  exitGroup,
  requestCloseGroup,
};
