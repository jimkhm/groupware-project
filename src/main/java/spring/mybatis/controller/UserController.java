package spring.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.mybatis.dao.UserDAOImpl;
import spring.mybatis.model.UserBean;

@Controller
public class UserController {

	@Autowired
	private UserDAOImpl userDAO;
	
	@ModelAttribute
	public UserBean formBacking(){
		return new UserBean();
	}
	
	@RequestMapping("/userlist.do")
	public ModelAndView userList(HttpServletRequest request){
		
		System.out.println("UserController : userList()");
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<UserBean> list = userDAO.getUserList();
		
//		for(UserBean user : list){
//			System.out.printf("id : %s, pwd : %s, name : %s", user.getUser_id(), user.getUser_pwd(), user.getUser_name());
//		}
		
		model.put("list", list);
		//mav.addObject("list", list); //request 영역에 해당 데이터를 저장해줌
		
		return new ModelAndView("user_list", model);
	}

	@RequestMapping("/userDetail.do")
	public ModelAndView userDetail(@RequestParam(value="id") String user_id){
		
		System.out.println("UserController : userDetail()");
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		UserBean user = userDAO.getUser(user_id);
		model.put("user", user);
		
		return new ModelAndView("user_detail", model);
	}
	
	@RequestMapping(value="/userNew.do", method=RequestMethod.GET)
	public String userNew(){
		
		System.out.println("UserController : userNew()");
		
		return "user_insert";
	}
	
	@RequestMapping(value="/userNew.do", method=RequestMethod.POST)
	public String userAdd(UserBean user){
		
		System.out.println("UserController : userAdd()");
		
		int rtn = userDAO.addUser(user);

		return "redirect:/userlist.do"; // 리스트 화면으로
	}
	
	@RequestMapping(value="/userModify.do", method=RequestMethod.GET)
	public ModelAndView userModify(@RequestParam(value="id") String user_id){
		
		System.out.println("UserController : userModify() : " + user_id);
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		UserBean user = userDAO.getUser(user_id);
		model.put("user", user);
		
		return new ModelAndView("user_update", model);
	}
	
	@RequestMapping(value="/userModify.do", method=RequestMethod.POST)
	public String userUpd(UserBean user){
		
		System.out.println("UserController : userUpd()");
		
		int rtn = userDAO.updUser(user);

		return "redirect:/userDetail.do?id=" + user.getUser_id(); // 조회 화면으로
	}
	
	@RequestMapping(value="/userDelete.do", method=RequestMethod.GET)
	public String userDelete(){
		
		System.out.println("UserController : userDelete()");
		
		return "user_delete";
	}
	
	@RequestMapping(value="/userDelete.do", method=RequestMethod.POST)
	public String userDel(@RequestParam(value="id") String user_id){
		
		System.out.println("UserController : userDel() : " + user_id);
		
		int rtn = userDAO.delUser(user_id);

		return "redirect:/userlist.do"; // 리스트 화면으로
	}
	
}
