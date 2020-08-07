package com.exe.board;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.exe.dao.BoardDAO;
import com.exe.dto.BoardDTO;
import com.exe.util.MyUtil;

@Controller
public class BoardController {
	
	@Autowired
	@Qualifier("boardDAO")
	BoardDAO dao;
	
	@Autowired
	@Qualifier("myUtil")
	MyUtil myUtil;
	
	@RequestMapping(value = "/created.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView created() {
		
		//return "bbs/created";
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/created");
		
		return mav;
	}
	
	@RequestMapping(value = "/created_ok.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String created_ok(BoardDTO dto, HttpServletRequest req, HttpServletResponse res) {
		
		int maxNum = dao.getMaxNum();
		
		dto.setNum(maxNum + 1);
		dto.setIpAddr(req.getRemoteAddr());
		
		dao.insert(dto);
		
		return "redirect:/list.action";
	}

	@RequestMapping(value = "/list.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String cp = req.getContextPath();	// /springboard�� ����
		
		String pageNum = req.getParameter("pageNum");
		int currentPage = 1;
		
		if(pageNum != null)		//�������ѹ� �޾ƿ����� ������������ ����
			currentPage = Integer.parseInt(pageNum);
		
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		
		if(searchKey == null){
			//searchKey������ �ʱⰪ
			searchKey = "subject";
			searchValue = "";
			
		}else{
			//searchKey������ ���־���
			if(req.getMethod().equalsIgnoreCase("GET"))
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		//��ü�����Ͱ���
		int dataCount = dao.getDataCount(searchKey, searchValue);	//�Խù� �� ����
		
		//��ü��������
		int numPerPage = 5;	//������ �ϳ��� ������ ������ ����
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);	//��ü ������ ����
		
		//������ ������ �� ������ �����Ǵ°��
		if(currentPage > totalPage)
			currentPage = totalPage;
		
		int start = (currentPage-1)*numPerPage+1;	//1,6,11, ...
		int end = currentPage*numPerPage;			//5,10,15, ...
		
		List<BoardDTO> lists =
			dao.list(start, end, searchKey, searchValue);
		
		//����¡ ó��
		String param = "";
		if(!searchValue.equals("")){
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" 
				+ URLEncoder.encode(searchValue, "UTF-8");
		}
		
		String listUrl = cp + "/list.action";
		if(!param.equals("")){
			listUrl = listUrl + "?" + param;
		}
		
		String pageIndexList =
			myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		//�ۺ��� �ּ� ����
		String articleUrl = 
			cp + "/article.action?pageNum=" + currentPage;
			
		if(!param.equals(""))
			articleUrl = articleUrl + "&" + param;
		
		//������ �� �������� �����͸� �ѱ��
		//req.setAttribute("lists", lists);
		//req.setAttribute("pageIndexList",pageIndexList);
		//req.setAttribute("dataCount",dataCount);
		//req.setAttribute("articleUrl",articleUrl);
		
		//return "bbs/list";
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/list");
		
		mav.addObject("lists", lists);
		mav.addObject("pageIndexList",pageIndexList);
		mav.addObject("dataCount",dataCount);
		mav.addObject("articleUrl",articleUrl);
		
		return mav;
	}
	
	@RequestMapping(value = "/article.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView article(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		int num = Integer.parseInt(req.getParameter("num"));
		String pageNum = req.getParameter("pageNum");
		
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		
		if(searchKey != null)
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		
		dao.updateHitCount(num);
		
		BoardDTO dto = dao.getData(num);
		
		int lineSu = dto.getContent().split("\n").length;	//�ټ� ���ͼ��� üũ
		
		dto.setContent(dto.getContent().replaceAll("\n", "<br/>"));	//�ٹٲ�üũ
		
		String param = "pageNum=" + pageNum;
		if(searchKey!=null){
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" 
				+ URLEncoder.encode(searchValue, "UTF-8");
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/article");
		
		mav.addObject("dto", dto);
		mav.addObject("lineSu", lineSu);
		mav.addObject("params", param);	//param��� params�� �ٸ� �̸�����...
		mav.addObject("pageNum", pageNum);
		
		return mav;
	}
	
	@RequestMapping(value = "/updated.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView updated(HttpServletRequest req, HttpServletResponse res) {
		
		int num = Integer.parseInt(req.getParameter("num"));
		String pageNum = req.getParameter("pageNum");
		
		BoardDTO dto = dao.getData(num);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/updated");
		
		mav.addObject("dto", dto);
		mav.addObject("pageNum", pageNum);
		
		return mav;
	}
	
	@RequestMapping(value = "/updated_ok.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String updated_ok(BoardDTO dto, HttpServletRequest req, HttpServletResponse res) {
	
		String pageNum = req.getParameter("pageNum");
		
		dao.updateData(dto);
		
		return "redirect:/list.action?pageNum=" + pageNum;
	}
	
	@RequestMapping(value = "/deleted.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleted(HttpServletRequest request, HttpServletResponse response) 
					throws Exception {

		int num =Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		dao.deleteData(num);
		
		return "redirect:/list.action?pageNum=" + pageNum;
	}
	
}
