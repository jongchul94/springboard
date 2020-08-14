package com.exe.board;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.exe.dao.BoardDAO;
import com.exe.dao.UserDAO;
import com.exe.dto.BoardDTO;
import com.exe.dto.UserDTO;
import com.exe.util.MyUtil;

@Controller
public class BoardController {
	
	@Autowired
	@Qualifier("boardDAO")
	BoardDAO bdao;
	
	@Autowired
	@Qualifier("userDAO")
	UserDAO udao;
	
	@Autowired
	@Qualifier("myUtil")
	MyUtil myUtil;
	
	//user
	@RequestMapping(value = "/regist.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView regist() {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("user/regist");
		
		return mav;
	}
	
	@RequestMapping(value = "/regist_ok.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String regist_ok(UserDTO dto, HttpServletRequest req, HttpServletResponse res) {
		
		udao.registUser(dto);
		
		return "redirect:/registOK.action";
	}
	
	@RequestMapping(value = "/registOK.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView registOK() {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("user/registOK");
		
		return mav;
	}
	
	@RequestMapping(value = "/login.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login() {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("user/login");
		
		return mav;
	}
	
	@RequestMapping(value = "/login_ok.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login_ok(String userID, String userPwd, HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		
		System.out.println(userID);
		System.out.println(userPwd);
		
		ModelAndView mav = new ModelAndView();
		
		UserDTO dto = new UserDTO();
		
		dto = udao.userOne(userID);
		
		//System.out.println(dto);
		//System.out.println(dto.getUserID());
		//System.out.println(dto.getUserPwd());
		
		if(dto==null || !dto.getUserPwd().equals(userPwd)) {		//	 || dto.getUserPwd()!=userPwd || !dto.getUserPwd().equals(userPwd) �۵���������...
			mav.setViewName("user/login");
			mav.addObject("message", "���̵� ���ų� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			
			return mav;
		}
		
		session.setAttribute("userID", dto.getUserID());
		session.setAttribute("userName", dto.getUserName());
		session.setAttribute("userEmail", dto.getUserEmail());
		
		mav.setView(new RedirectView("list.action"));
		
		return mav;
	}
	
	@RequestMapping(value = "/logout.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		
		session.removeAttribute("userID");
		session.removeAttribute("userName");
		session.removeAttribute("userEmail");
		
		mav.setView(new RedirectView("list.action"));
		
		return mav;
	}
	
	
	
	
	
	
	//board
	@RequestMapping(value = "/created.action", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView created() {
		
		//return "bbs/created";
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/created");
		
		return mav;
	}
	
	@RequestMapping(value = "/created_ok.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String created_ok(BoardDTO dto, HttpServletRequest req, HttpServletResponse res) {
		
		int maxNum = bdao.getMaxNum();
		
		dto.setNum(maxNum + 1);
		dto.setIpAddr(req.getRemoteAddr());
		
		bdao.insert(dto);
		
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
		int dataCount = bdao.getDataCount(searchKey, searchValue);	//�Խù� �� ����
		
		//��ü��������
		int numPerPage = 5;	//������ �ϳ��� ������ ������ ����
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);	//��ü ������ ����
		
		//������ ������ �� ������ �����Ǵ°��
		if(currentPage > totalPage)
			currentPage = totalPage;
		
		//����¡ ����,��
		int start = (currentPage-1)*numPerPage+1;	//1,6,11, ...
		int end = currentPage*numPerPage;			//5,10,15, ...
		
		List<BoardDTO> lists =
			bdao.list(start, end, searchKey, searchValue);
		
		//����¡ ó��
		String param = "";
		if(!searchValue.equals("")){
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" 
				+ URLEncoder.encode(searchValue, "UTF-8");
		}
		
		// ������ URL�� ���� �⺻ URL --- listUrl = "localhost:8080/springboard/list.action?searchKey=''&searchValue=''";
		String listUrl = cp + "/list.action";
		if(!param.equals("")){
			listUrl = listUrl + "?" + param;
		}
		
		String pageIndexList =
			myUtil.pageIndexList(currentPage, totalPage, listUrl);	//����¡ó�� String�� �Ѿ��, ex. ������ 6 7 8 9 10 ������
		
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
		
		mav.addObject("lists", lists);			//�Խù� ������ List�� ��Ƴ���
		mav.addObject("pageIndexList",pageIndexList);	//����¡ó��
		mav.addObject("dataCount",dataCount);	//��ü ������ ����, �Խù� ���翩��
		mav.addObject("articleUrl",articleUrl);	//�Խù� ��ũ
		
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
		
		bdao.updateHitCount(num);
		
		BoardDTO dto = bdao.getData(num);
		
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
		
		BoardDTO dto = bdao.getData(num);
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/updated");
		
		mav.addObject("dto", dto);
		mav.addObject("pageNum", pageNum);
		
		return mav;
	}
	
	@RequestMapping(value = "/updated_ok.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String updated_ok(BoardDTO dto, HttpServletRequest req, HttpServletResponse res) {
	
		String pageNum = req.getParameter("pageNum");
		
		bdao.updateData(dto);
		
		return "redirect:/list.action?pageNum=" + pageNum;
	}
	
	@RequestMapping(value = "/deleted.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleted(HttpServletRequest request, HttpServletResponse response) 
					throws Exception {

		int num =Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		bdao.deleteData(num);
		
		return "redirect:/list.action?pageNum=" + pageNum;
	}
	
}
