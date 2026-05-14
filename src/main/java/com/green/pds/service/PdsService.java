package com.green.pds.service;

import java.util.HashMap;
import java.util.List;

import com.green.pds.dto.PdsDTO;

public interface PdsService {

	List<PdsDTO> getPdsList(HashMap<String, Object> map);

}
