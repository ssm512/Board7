package com.green.pds.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.green.menus.dto.MenuDTO;
import com.green.menus.mapper.MenuMapper;
import com.green.paging.dto.Pagination;
import com.green.paging.dto.SearchDTO;
import com.green.pds.dto.PdsDTO;
import com.green.pds.mapper.PdsMapper;
import com.green.pds.service.PdsService;

@Controller
@RequestMapping("/Pds")
public class PdsController {
	
	@Autowired
	private	MenuMapper menuMapper;
	
	@Autowired
	private	PdsMapper	pdsMapper;
	
	@Autowired
	private	PdsService pdsService;
	
	// /Pds/List?menu_id=MENU01&nowpage=1
	// /Pds/List?menu_id=MENU01&nowpage=6&searchType=&keyword=
	@RequestMapping("/List")
	public ModelAndView list ( @RequestParam HashMap<String, Object> map ) {
		System.out.println("map:" + map);  
		// map:{menu_id=MENU01, nowpage=1}
		// map:{menu_id=MENU01, nowpage=1, searchType=, keyword=}
		
		// 전체 메뉴 목록 조회
		List<MenuDTO> menuList	=	menuMapper.getMenuList();
		
		// 자료실 목록 조회 (10개씩)
		// 해당 메뉴의 전체 자료수
		int totalCount	=	pdsMapper.count(map); // menu_id, searchType, keyword 전달하여 찾을 예정임
		//System.out.println("totalCount : " + totalCount);
		
		// 페이징을 위한 설정
		int		nowpage		=	Integer.parseInt( String.valueOf(map.get("nowpage")) );
		SearchDTO	searchDTO	=	new	SearchDTO();
		searchDTO.setPageNo( nowpage ); // 현재 페이지 설정
		searchDTO.setNumOfRows(10); // 한페이지에 10줄의 자료를 출력
		searchDTO.setPageSize(10);	// 페이지 번호 목록
		
		// Pagination 설정
		Pagination pagination = new Pagination(totalCount, searchDTO);
		searchDTO.setPagination(pagination);
		
		int		offset		=	searchDTO.getOffset();
		int		numOfRows	=	searchDTO.getNumOfRows();
		
		map.put("offset", offset);
		map.put("numOfRows", numOfRows);
		
		System.out.println("map2 : " + map);
		
		// 자료 조회
		List<PdsDTO> pdsList		=	pdsService.getPdsList( map );
		
		// 넘길값
		ModelAndView mv	=	new	ModelAndView();
		mv.setViewName("pds/list");
		
		mv.addObject("menuList", menuList);
		mv.addObject("searchDTO",searchDTO);
		mv.addObject("pdsList", pdsList);
		mv.addObject("map", map);
		return mv;
	}
}
