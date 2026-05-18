package com.green.pds.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.green.pds.dto.FilesDTO;
import com.green.pds.dto.PdsDTO;
import com.green.pds.mapper.PdsMapper;
import com.green.pds.service.PdsService;

@Service
public class PdsServiceImpl implements PdsService { // 인터페이스 implement한 class
	
	// @Value가 application.properties에 있는 
	// part1.upload-path
	// org.springframework.beans.factory.annotation.Value 이걸 import해야됨
	@Value("${part1.upload-path}")
	private	String uploadPath;
	
	@Autowired
	private	PdsMapper pdsMapper;
	
	@Override
	public List<PdsDTO> getPdsList(HashMap<String, Object> map) {
		List<PdsDTO> pdsList = pdsMapper.getPdsList(map);
				
		return pdsList;
	}

	@Override
	public void setWrite(
			HashMap<String, Object> map, 
			MultipartFile[] uploadfiles	) {
		// 파일저장 + db저장
		// 1. 파일저장 - uploadfiles [] -> upload Path : d:/dev/springboot/data/
		
		//String uploadPath = "d:/dev/springboot/data/"; // application.properties에 들어있음
		map.put("uploadPath", uploadPath);
		System.out.println("PdsFile 이전 map : " + map);
		// map{ menu_id=MENU01 , nowpage=1, title=asdv, writer=admin,
		// content=alkjzxcvme, uploadPath=d:/dev/springboot/data/ }
		
		// 별도 클래스 생성해서 처리 : PdsFile
		// PdsFile은 핵심로직은 아님
		// 넘어온 정보로 파일을 저장
		PdsFile.save(map, uploadfiles);
		
		System.out.println("PdsFile 이후 map : " + map);
		// map : {menu_id=MENU01, nowpage=1, title=aaa, writer=admin, content=acvasdfvcx, uploadPath=d:/dev/springboot/data/, 
		// fileList=[
		//	FilesDTO(file_num=0, idx=0, filename=adInterceptor.txt, fileext=.txt, sfilename=2026\05\18\b202a39b-bfb1-4a08-ad7d-581d906d667b.adInterceptor.txt)
		// ] }
				
		// 2. db 저장 : 자료실 글 쓰기 - map
		// Board table의 저장
		pdsMapper.setWrite( map );	// insertboard를 setWrite로 통일할 예정임
		
		// 3. Files에 저장 
		List<FilesDTO> fileList = (List<FilesDTO>) map.get("fileList");
		if (fileList.size() > 0)
			pdsMapper.setFileWriter(map);
	}

}
