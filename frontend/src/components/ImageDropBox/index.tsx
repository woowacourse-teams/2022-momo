import { useState } from 'react';

import { useQueryClient } from 'react-query';
import { useRecoilValue } from 'recoil';

import {
  requestEditThumbnail,
  requestResetThumbnail,
} from 'apis/request/group';
import Modal from 'components/Modal';
import { QUERY_KEY } from 'constants/key';
import { CLIENT_ERROR_MESSAGE, GUIDE_MESSAGE } from 'constants/message';
import useHandleError from 'hooks/useHandleError';
import useModal from 'hooks/useModal';
import useSnackbar from 'hooks/useSnackbar';
import { modalState } from 'store/states';
import { GroupSummary } from 'types/data';

import * as S from './index.styled';

function ImageDropBox({ id }: Pick<GroupSummary, 'id'>) {
  const [file, setFile] = useState<File | null>();
  const [isDragOver, setIsDragOver] = useState(false);

  const modalFlag = useRecoilValue(modalState);
  const { setOffModal } = useModal();
  const { setMessage } = useSnackbar();
  const { handleError } = useHandleError();

  const queryClient = useQueryClient();

  const updateFile = (newFile: File) => {
    if (
      newFile.type !== 'image/png' &&
      newFile.type !== 'image/jpg' &&
      newFile.type !== 'image/jpeg'
    ) {
      setMessage(CLIENT_ERROR_MESSAGE.GROUP.NOT_ALLOWED_THUMBNAIL_TYPE, true);
      return;
    }

    setFile(newFile);
  };

  const dragOverOnLabel = (e: React.DragEvent<HTMLLabelElement>) => {
    e.preventDefault();
    e.stopPropagation();

    setIsDragOver(true);
  };

  const dragLeaveOnLabel = (e: React.DragEvent<HTMLLabelElement>) => {
    e.preventDefault();
    e.stopPropagation();

    setIsDragOver(false);
  };

  const dropFile = (e: React.DragEvent) => {
    e.preventDefault();

    if (!e.dataTransfer?.files || e.dataTransfer?.files.length <= 0) return;

    const newFile = e.dataTransfer.files[0];

    updateFile(newFile);
  };

  const changeFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files || e.target.files.length <= 0) return;

    const newFile = e.target.files[0];

    updateFile(newFile);
  };

  const editThumbnail = (e: React.FormEvent) => {
    e.preventDefault();

    if (!file) {
      setMessage(CLIENT_ERROR_MESSAGE.GROUP.NO_THUMBNAIL, true);
      return;
    }

    if (
      file.type !== 'image/png' &&
      file.type !== 'image/jpg' &&
      file.type !== 'image/jpeg'
    ) {
      setMessage(CLIENT_ERROR_MESSAGE.GROUP.NOT_ALLOWED_THUMBNAIL_TYPE, true);
      return;
    }

    const formData = new FormData();
    formData.append('file', file);

    requestEditThumbnail(id, formData)
      .then(() => {
        setFile(null);
        queryClient.invalidateQueries(QUERY_KEY.GROUP_DETAILS);

        setMessage(GUIDE_MESSAGE.GROUP.SUCCESS_EDIT_THUMBNAIL);
        setOffModal();
      })
      .catch(error => {
        if (!error) {
          alert(CLIENT_ERROR_MESSAGE.GROUP.FAILURE_EDIT_THUMBNAIL);
        }
        handleError(error);
      });
  };

  const resetThumbnail = (e: React.FormEvent) => {
    e.preventDefault();

    if (!window.confirm(GUIDE_MESSAGE.DELETE.CONFIRM_THUMBNAIL_REQUEST)) return;

    requestResetThumbnail(id)
      .then(() => {
        setFile(null);
        queryClient.invalidateQueries(QUERY_KEY.GROUP_DETAILS);

        setMessage(GUIDE_MESSAGE.DELETE.SUCCESS_THUMBNAIL_REQUEST);
        setOffModal();
      })
      .catch(error => {
        if (!error) {
          alert(CLIENT_ERROR_MESSAGE.DELETE.FAILURE_THUMBNAIL_REQUEST);
        }
        handleError(error);
      });
  };

  return (
    <Modal modalState={modalFlag === 'thumbnail'}>
      <S.Form onSubmit={editThumbnail}>
        <S.Title>썸네일 수정</S.Title>
        <S.Label
          htmlFor="file"
          isDragOver={isDragOver}
          onDrop={dropFile}
          onDragOver={dragOverOnLabel}
          onDragLeave={dragLeaveOnLabel}
        >
          {file ? (
            <S.Image src={URL.createObjectURL(file)} alt="thumbnail" />
          ) : (
            <p>드래그 또는 클릭하여 이미지를 추가하세요</p>
          )}
        </S.Label>
        <S.FileInput
          id="file"
          type="file"
          accept=".jpg, .jpeg, .png"
          onChange={changeFile}
        />
        <S.ButtonContainer>
          <S.ResetButton type="button" onClick={resetThumbnail}>
            기본 이미지로 수정
          </S.ResetButton>
          <S.EditButton>수정</S.EditButton>
        </S.ButtonContainer>
      </S.Form>
    </Modal>
  );
}

export default ImageDropBox;
