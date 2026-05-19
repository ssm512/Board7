package com.green.pds.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// map(idx)에 해당하는 글 조회수 증가
	@Override
	public void setReadCountUpdate(HashMap<String, Object> map) {
		pdsMapper.setReadCountUpdate(map);
		
	}

	// 자료실 게시글(pds)을 조회한다. : map(idx)
	@Override
	public PdsDTO getPds(HashMap<String, Object> map) {
		PdsDTO pdsDTO = pdsMapper.getPds(map);
		return pdsDTO;
	}

	// idx에 해당하는 Files table의 정보 - Files table의 idx에 해당하는 list
	@Override
	public List<FilesDTO> getFileList(HashMap<String, Object> map) {
		List<FilesDTO> fileList = pdsMapper.getFileList(map);
		return fileList;
	}

	// file_num로 조회할 파일 정보를 조회 - Files table의 한줄 
	@Override
	public FilesDTO getFileInfo(Long file_num) {
		FilesDTO fileInfo = pdsMapper.getFileInfo(file_num);
		return fileInfo;
	}

	// 자료실 자료 삭제
	// 외래키를 참고하므로 삭제 순서도 중요함
	// 자식부터 삭제해야됨
	// /Pds/Delete?idx=1416&menu_id=MENU02&nowpage=1
	@Override
	public void setDelete(HashMap<String, Object> map) {
		// 0. 해당파일 정보 조회
		List<FilesDTO> fileList = pdsMapper.getFileList(map);
		
		// 1. 실제 파일도 삭제 d:\dev\springboot\data 에 있는 idx 관련 파일 삭제
		PdsFile.delete(uploadPath, fileList);
		
		// 2. idx에 해당하는 파일 삭제 : files table에 실제 삭제된 정보를 지운다
		// 외래키가 설정된 관계에서 삭제는 자식레코드를 먼저 삭제해야한다
		pdsMapper.deleteUploadFile(map);
		
		// 3. idx에 해당하는 자료실 글 삭제 : board
		pdsMapper.setDelete(map);
	}

	// 자료실 자료 수정
	// map {menu_id=MENU01, nowpage=1, idx=1415, title=자바 새글 등록 02 수정,
	// content=asdf	asdflk 	수정} 
	// MultipartFile { upfile=(binary), upfile=(binary) }
	@Override
	public void setUpdate(HashMap<String, Object> map, MultipartFile[] uploadfiles) {

		// 업로드될 경로를 map에 추가
		map.put("uploadPath", uploadPath);
		
		// 업로드된 파일 저장
		PdsFile.save(map, uploadfiles); // 업로드 하고 나면 map (fileList가 추가됨)
		
		// Files table에 정보저장 <- map <- fileList
		List<FilesDTO> fileList = (List<FilesDTO>) map.get("fileList");
		if (fileList.size()>0)
			pdsMapper.setFileWriter(map);
		
		// Board table 필요한 정보를 수정
		pdsMapper.setUpdate(map);
	}

}
