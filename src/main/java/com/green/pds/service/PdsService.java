package com.green.pds.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.green.pds.dto.PdsDTO;

public interface PdsService {

	List<PdsDTO> getPdsList(HashMap<String, Object> map);

	void setWrite(HashMap<String, Object> map, MultipartFile[] uploadfiles);

}
