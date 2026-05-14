package com.green.pds.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.pds.dto.PdsDTO;
import com.green.pds.mapper.PdsMapper;
import com.green.pds.service.PdsService;

@Service
public class PdsServiceImpl implements PdsService { // 인터페이스 implement한 class

	@Autowired
	private	PdsMapper pdsMapper;
	
	@Override
	public List<PdsDTO> getPdsList(HashMap<String, Object> map) {
		List<PdsDTO> pdsList = pdsMapper.getPdsList(map);
				
		return pdsList;
	}

}
