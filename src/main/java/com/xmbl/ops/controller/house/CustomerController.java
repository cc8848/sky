package com.xmbl.ops.controller.house;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmbl.ops.constant.SessionConstant;
import com.xmbl.ops.controller.organization.AbstractController;
import com.xmbl.ops.dto.ResponseResult;
import com.xmbl.ops.enumeration.EnumResCode;
import com.xmbl.ops.model.house.BaseHouse;
import com.xmbl.ops.model.house.BlackAgent;
import com.xmbl.ops.model.house.Customer;
import com.xmbl.ops.model.house.OperatorLog;
import com.xmbl.ops.service.house.CustomerService;
import com.xmbl.ops.util.DateUtils;




@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends AbstractController {

	private static final int limit = 30;

	@Autowired
	protected CustomerService customerService;
	
	//管理员查看--客户列表
	@RequestMapping(value = "/customerList")
	public String customerSearch(HttpServletRequest request, ModelMap model, 
			Integer id,
			String usename, String gender, String mobile,
			String phone, String nickname, String qq, 
			String wechat, String email ,String source,
			String address, String remarks, Integer status,
			String operator,
			String startTime, String endTime, Long page) {
		try{			
			HttpSession session = request.getSession();
			String groupName = (String) session.getAttribute(SessionConstant.GROUP_NAME);
			String userKey = (String) session.getAttribute(SessionConstant.USER_NAME);

			Date startDate = DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss");
			Date endDate = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss");
//			if (StringUtils.isNotEmpty(usename)) {
//				usename = new String(usename.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(nickname)) {
//				nickname = new String(nickname.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(email)) {
//				email = new String(email.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(gender)) {
//				gender = new String(gender.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(source)) {
//				source = new String(source.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(remarks)) {
//				remarks = new String(remarks.getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(address)) {
//				address = new String(address.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}

			page = page == null || page < 0 ? 0 : page;

			long totalNum = customerService.searchCount(id,usename,gender, mobile,
				     phone, nickname, qq,
				     wechat,  email, source,
				     address, status, 
				     remarks, operator,startDate, endDate);

			long totalPageNum = totalNum / limit;
			if(totalNum > totalPageNum * limit)
				totalPageNum++;
			if(page >= totalPageNum && totalPageNum != 0)
				page = totalPageNum - 1;
			long start = page * limit;
			List<Customer> customerList = customerService.searchList(
					id,usename,gender, mobile,
				     phone, nickname, qq,
				     wechat,  email, source,
				     address, status, 
				     remarks, operator,startDate, endDate, start, limit);
			model.addAttribute("id", id);
			model.addAttribute("usename", usename);
			model.addAttribute("gender", gender);
			model.addAttribute("mobile", mobile);
			model.addAttribute("phone", phone);
			model.addAttribute("nickname", nickname);
			model.addAttribute("qq", qq);
			model.addAttribute("wechat", wechat);
			model.addAttribute("email", email);
			model.addAttribute("source", source);
			model.addAttribute("address", address);
			model.addAttribute("operator", operator);
			model.addAttribute("status", status);
			model.addAttribute("remarks", remarks);
			model.addAttribute("startTime", startTime);
			model.addAttribute("endTime", endTime);
			model.addAttribute("customerList", customerList);
			model.addAttribute("page", page);
			model.addAttribute("totalNum", totalNum);
			model.addAttribute("totalpage", totalPageNum);	
			return "house/customer/customertList";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	//经纪人 客户列表
	@RequestMapping(value = "/agentCustomerList")
	public String agentCustomerSearch(HttpServletRequest request, ModelMap model, 
			Integer id,
			String usename, String gender, String mobile,
			String phone, String nickname, String qq, 
			String wechat, String email ,String source,
			String address, String remarks, Integer status,
			String operator,
			String startTime, String endTime, Long page) {
		try{			
			HttpSession session = request.getSession();
			String groupName = (String) session.getAttribute(SessionConstant.GROUP_NAME);
			String userKey = (String) session.getAttribute(SessionConstant.USER_NAME);

			Date startDate = DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss");
			Date endDate = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss");
//			if (StringUtils.isNotEmpty(usename)) {
//				usename = new String(usename.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(nickname)) {
//				nickname = new String(nickname.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(email)) {
//				email = new String(email.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(gender)) {
//				gender = new String(gender.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(source)) {
//				source = new String(source.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(remarks)) {
//				remarks = new String(remarks.getBytes("ISO-8859-1"), "UTF-8");
//			}
//			if (StringUtils.isNotEmpty(address)) {
//				address = new String(address.trim().getBytes("ISO-8859-1"), "UTF-8");
//			}
			operator = userKey;
			page = page == null || page < 0 ? 0 : page;

			long totalNum = customerService.agentSearchCount(id,usename,gender, mobile,
				     phone, nickname, qq,
				     wechat,  email, source,
				     address, status, 
				     remarks, operator,startDate, endDate);

			long totalPageNum = totalNum / limit;
			if(totalNum > totalPageNum * limit)
				totalPageNum++;
			if(page >= totalPageNum && totalPageNum != 0)
				page = totalPageNum - 1;
			long start = page * limit;
			List<Customer> customerList = customerService.agentSearchList(
					id,usename,gender, mobile,
				     phone, nickname, qq,
				     wechat,  email, source,
				     address, status, 
				     remarks, operator,startDate, endDate, start, limit);
			model.addAttribute("id", id);
			model.addAttribute("usename", usename);
			model.addAttribute("gender", gender);
			model.addAttribute("mobile", mobile);
			model.addAttribute("phone", phone);
			model.addAttribute("nickname", nickname);
			model.addAttribute("qq", qq);
			model.addAttribute("wechat", wechat);
			model.addAttribute("email", email);
			model.addAttribute("source", source);
			model.addAttribute("address", address);
			model.addAttribute("operator", operator);
			model.addAttribute("status", status);
			model.addAttribute("remarks", remarks);
			model.addAttribute("startTime", startTime);
			model.addAttribute("endTime", endTime);
			model.addAttribute("customerList", customerList);
			model.addAttribute("page", page);
			model.addAttribute("totalNum", totalNum);
			model.addAttribute("totalpage", totalPageNum);	
			return "house/customer/agentCustomertList";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//淘宝池 客户列表--失效客户
		@RequestMapping(value = "/taobaoCustomerList")
		public String taobaoCustomerSearch(HttpServletRequest request, ModelMap model, 
				Integer id,
				String usename, String gender, String mobile,
				String phone, String nickname, String qq, 
				String wechat, String email ,String source,
				String address, String remarks, Integer status,
				String operator,
				String startTime, String endTime, Long page) {
			try{			
				HttpSession session = request.getSession();
				String groupName = (String) session.getAttribute(SessionConstant.GROUP_NAME);
				String userKey = (String) session.getAttribute(SessionConstant.USER_NAME);

				Date startDate = DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss");
				Date endDate = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss");
//				if (StringUtils.isNotEmpty(usename)) {
//					usename = new String(usename.trim().getBytes("ISO-8859-1"), "UTF-8");
//				}
//				if (StringUtils.isNotEmpty(nickname)) {
//					nickname = new String(nickname.trim().getBytes("ISO-8859-1"), "UTF-8");
//				}
//				if (StringUtils.isNotEmpty(email)) {
//					email = new String(email.trim().getBytes("ISO-8859-1"), "UTF-8");
//				}
//				if (StringUtils.isNotEmpty(gender)) {
//					gender = new String(gender.trim().getBytes("ISO-8859-1"), "UTF-8");
//				}
//				if (StringUtils.isNotEmpty(source)) {
//					source = new String(source.trim().getBytes("ISO-8859-1"), "UTF-8");
//				}
//				if (StringUtils.isNotEmpty(remarks)) {
//					remarks = new String(remarks.getBytes("ISO-8859-1"), "UTF-8");
//				}
//				if (StringUtils.isNotEmpty(address)) {
//					address = new String(address.trim().getBytes("ISO-8859-1"), "UTF-8");
//				}
//				operator = userKey;
				page = page == null || page < 0 ? 0 : page;

				long totalNum = customerService.agentSearchCount(id,usename,gender, mobile,
					     phone, nickname, qq,
					     wechat,  email, source,
					     address, 3, 
					     remarks, operator,startDate, endDate);

				long totalPageNum = totalNum / limit;
				if(totalNum > totalPageNum * limit)
					totalPageNum++;
				if(page >= totalPageNum && totalPageNum != 0)
					page = totalPageNum - 1;
				long start = page * limit;
				List<Customer> customerList = customerService.agentSearchList(
						id,usename,gender, mobile,
					     phone, nickname, qq,
					     wechat,  email, source,
					     address, 3, 
					     remarks, operator,startDate, endDate, start, limit);
				model.addAttribute("id", id);
				model.addAttribute("usename", usename);
				model.addAttribute("gender", gender);
				model.addAttribute("mobile", mobile);
				model.addAttribute("phone", phone);
				model.addAttribute("nickname", nickname);
				model.addAttribute("qq", qq);
				model.addAttribute("wechat", wechat);
				model.addAttribute("email", email);
				model.addAttribute("source", source);
				model.addAttribute("address", address);
				model.addAttribute("operator", operator);
				model.addAttribute("status", status);
				model.addAttribute("remarks", remarks);
				model.addAttribute("startTime", startTime);
				model.addAttribute("endTime", endTime);
				model.addAttribute("customerList", customerList);
				model.addAttribute("page", page);
				model.addAttribute("totalNum", totalNum);
				model.addAttribute("totalpage", totalPageNum);	
				return "house/customer/taobaoCustomertList";
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
	
   //添加客户
	@RequestMapping(value = "/addCustomer")
	public @ResponseBody ResponseResult addCustomer(HttpServletRequest request, String usename, String gender, String mobile, String phone, String nickname, String qq, String wechat, String email ,String source, String address, String remarks, Integer status) {
		try {
			if (StringUtils.isEmpty(usename))
				return errorJson(EnumResCode.SERVER_ERROR.value(), "客户名称不能为空");
//			usename = new String(usename.getBytes("ISO-8859-1"), "UTF-8");
			
			if (StringUtils.isEmpty(mobile))
				return errorJson(EnumResCode.SERVER_ERROR.value(), "电话不能为空");
			
			if (StringUtils.isNotEmpty(qq)) {
				Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");   
		        Matcher isNum = pattern.matcher(qq.trim());  
		        if( !isNum.matches() ){  
		        	   return errorJson(EnumResCode.SERVER_ERROR.value(), "qq必须是数字"); 
		        }
			}		
//			nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
//			email = new String(email.getBytes("ISO-8859-1"), "UTF-8");
//			gender = new String(gender.getBytes("ISO-8859-1"), "UTF-8");
//			source = new String(source.getBytes("ISO-8859-1"), "UTF-8");
//			remarks = new String(remarks.getBytes("ISO-8859-1"), "UTF-8");
//			address = new String(address.getBytes("ISO-8859-1"), "UTF-8");
			
			HttpSession session = request.getSession();
			String operator = (String) session.getAttribute(SessionConstant.USER_NAME);
			if(StringUtils.isNotEmpty(usename))
				usename = usename.replaceAll("'&nbsp';", "&nbsp");
			Customer customer = new Customer(usename,gender, mobile,
				     phone,nickname,qq,
				     wechat, email,source,
				     address,status, 
				     remarks,operator);
			customer = customerService.insertSelective(customer);
			if(customer != null)
				return successJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorJson(EnumResCode.SERVER_ERROR.value(), "新增客户失败");
	}
	
	   //添加经纪人客户
		@RequestMapping(value = "/addAgentCustomer")
		public @ResponseBody ResponseResult addAgentCustomer(HttpServletRequest request, String usename, String gender, String mobile, String phone, String nickname, String qq, String wechat, String email ,String source, String address, String remarks, Integer status) {
			try {
				if (StringUtils.isEmpty(usename))
					return errorJson(EnumResCode.SERVER_ERROR.value(), "客户名称不能为空");
//				usename = new String(usename.getBytes("ISO-8859-1"), "UTF-8");
				
				if (StringUtils.isEmpty(mobile))
					return errorJson(EnumResCode.SERVER_ERROR.value(), "电话不能为空");
				
				if (StringUtils.isNotEmpty(qq)) {
					Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");   
			        Matcher isNum = pattern.matcher(qq.trim());  
			        if( !isNum.matches() ){  
			        	   return errorJson(EnumResCode.SERVER_ERROR.value(), "qq必须是数字"); 
			        }
				}		
//				nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
//				email = new String(email.getBytes("ISO-8859-1"), "UTF-8");
//				gender = new String(gender.getBytes("ISO-8859-1"), "UTF-8");
//				source = new String(source.getBytes("ISO-8859-1"), "UTF-8");
//				remarks = new String(remarks.getBytes("ISO-8859-1"), "UTF-8");
//				address = new String(address.getBytes("ISO-8859-1"), "UTF-8");
				
				HttpSession session = request.getSession();
				String operator = (String) session.getAttribute(SessionConstant.USER_NAME);
				if(StringUtils.isNotEmpty(usename))
					usename = usename.replaceAll("'&nbsp';", "&nbsp");
				Customer customer = new Customer(usename,gender, mobile,
					     phone,nickname,qq,
					     wechat, email,source,
					     address,status, 
					     remarks,operator);
				customer = customerService.insertSelective(customer);
				if(customer != null)
					return successJson();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return errorJson(EnumResCode.SERVER_ERROR.value(), "新增经纪人客户失败");
		}
	
	   //添加客户
		@RequestMapping(value = "/instertCustomer")
		public String instertCustomer(HttpServletRequest request, String usename, String gender, String mobile, String phone, String nickname, String qq, String wechat, String email ,String source, String address, String remarks, Integer status) {
			return "house/customer/addCustomer";
		}
	
		 //添加客户
			@RequestMapping(value = "/instertAgentCustomer")
			public String instertAgentCustomer(HttpServletRequest request, String usename, String gender, String mobile, String phone, String nickname, String qq, String wechat, String email ,String source, String address, String remarks, Integer status) {
				return "house/customer/addAgentCustomer";
			}
		
		//详细
		@RequestMapping(value = "/getCustomer")
		public String getCustomer(HttpServletRequest request,ModelMap model,  Integer id) {
			if(id == null) {
				return "客户存在！";
			}
			HttpSession session = request.getSession();
			String operator = (String) session
					.getAttribute(SessionConstant.USER_NAME);
			
			
			Customer customerInfo = customerService.getById(id);
			
			model.addAttribute("customerInfo", customerInfo);
			
			
			return "house/customer/customerDetail";
		}
		
		//详细
		@RequestMapping(value = "/getAgentCustomer")
		public String getAgentCustomer(HttpServletRequest request,ModelMap model,  Integer id) {
			if(id == null) {
				return "客户存在！";
			}
			HttpSession session = request.getSession();
			String operator = (String) session
					.getAttribute(SessionConstant.USER_NAME);
			
			
			Customer customerInfo = customerService.getById(id);
			
			model.addAttribute("customerInfo", customerInfo);
			
			
			return "house/customer/agentCustomerDetail";
		}
		
		/**
		 * 编辑
		 */
		@RequestMapping(value = "/updateCustomer")
		public @ResponseBody ResponseResult updateCustomer(HttpServletRequest request,Integer id, String usename,String gender,String mobile,
	    	     String phone,String nickname,String qq,
	    	     String wechat, String email,String source,
	    	     String address,Integer status, 
	    	     String remarks) throws IOException{
			if(StringUtils.isEmpty(usename)) {
				return errorJson(EnumResCode.SERVER_ERROR.value(), "姓名不能为空！");
			}
			if(StringUtils.isEmpty(phone)) {
				return errorJson(EnumResCode.SERVER_ERROR.value(), "电话不能为空！");
			}

			HttpSession session = request.getSession();
			String operator = (String) session
					.getAttribute(SessionConstant.USER_NAME);
			
			Customer customerInfo = new Customer(id,usename,gender, mobile,
				     phone,nickname,qq,
				     wechat, email,source,
				     address,status, 
				     remarks,operator);
	        
			int updateNum = customerService.updateCustomer(id, usename,gender, mobile,
				     phone,nickname,qq,
				     wechat, email,source,
				     address,status, 
				     remarks,operator);
			if (updateNum == 1) {
				customerInfo = customerService.getById(id);
				if( customerInfo != null ){      
	        	JSONObject result = new JSONObject();
	    		//result中保存要传给前台的参数
	        	result.put("id",customerInfo.getId().toString());
	    		result.put("usename", customerInfo.getUsename());
	    		result.put("phone", customerInfo.getPhone());
	    		result.put("remarks", customerInfo.getRemarks());
	    		result.put("operator", customerInfo.getOperator());
	    		result.put("createtime", customerInfo.getCreatetime());
	    		result.put("updatetime", customerInfo.getUpdatetime());
	        	return successJson(result);
	        	}
			}
			return errorJson(EnumResCode.SERVER_ERROR.value(), "编辑失败！");
		}
		
	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteCustomer")
	public @ResponseBody ResponseResult deleteCustomer(HttpServletRequest request, Integer id) throws IOException{
		    customerService.deleteCustomer(id);
			JSONObject result = new JSONObject();
			result.put("id", id);
			return successJson(result);

	}
		
}
