package com.green.pds.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.green.pds.dto.FilesDTO;
import com.green.pds.dto.PdsDTO;

public interface PdsService {

	List<PdsDTO> getPdsList(HashMap<String, Object> map);

	void setWrite(HashMap<String, Object> map, MultipartFile[] uploadfiles);

	void setReadCountUpdate(HashMap<String, Object> map);

	PdsDTO getPds(HashMap<String, Object> map);

	List<FilesDTO> getFileList(HashMap<String, Object> map);

	FilesDTO getFileInfo(Long file_num);

	void setDelete(HashMap<String, Object> map);

	void setUpdate(HashMap<String, Object> map, MultipartFile[] uploadfiles);

}
