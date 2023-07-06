package kr.opensoftlab.lunaops.com.api.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.fdl.property.EgovPropertyService;
import kr.opensoftlab.lunaops.com.api.service.ApiService;
import kr.opensoftlab.lunaops.com.vo.OslErrorCode;
import kr.opensoftlab.sdf.util.RequestConvertor;



@Controller
public class ApiController {

	
	private static final Logger Log = Logger.getLogger(ApiController.class);

	
	@Resource(name = "apiService")
	protected ApiService apiService;
	
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	
	@RequestMapping(value="/api/insertCIRepJenJob")
	public ModelAndView insertCIRepJenJob(HttpServletRequest request, HttpServletResponse response, ModelMap model ) throws Exception {
		try{
			
			Map<String, String> paramMap = RequestConvertor.requestParamToMapAddSelInfo(request, true);
			
			
			String rtnCode = apiService.insertCIRepJenJob(paramMap);

			
			if("-1".equals(rtnCode)) {
				model.addAttribute("result", true);
				model.addAttribute("message", "정상적으로 등록되었습니다.");
			}else {
				model.addAttribute("result", false);
				model.addAttribute("errorCode", rtnCode);
				model.addAttribute("message", "오류가 발생했습니다.");
			}
		}catch(Exception ex){
			model.addAttribute("result", false);
			model.addAttribute("errorCode", OslErrorCode.SERVER_ERROR);
			model.addAttribute("message", "오류가 발생했습니다.");
			Log.error("insertCIRepJenJob()", ex);
			
		}
		 return new ModelAndView("jsonView");
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/api/selectCIRepJenJob")
	public ModelAndView selectCIRepJenJob(HttpServletRequest request, HttpServletResponse response, ModelMap model ) throws Exception {
		try{
			
			Map<String, String> paramMap = RequestConvertor.requestParamToMapAddSelInfo(request, true);
			
			
			Map ciRepJenJobList = apiService.selectCIRepJenJob(paramMap);
			
			
			if(ciRepJenJobList.containsKey("IS_ERROR") && (boolean) ciRepJenJobList.get("IS_ERROR")) {
				model.addAttribute("result", false);
				model.addAttribute("errorCode", ciRepJenJobList.get("ERROR_CODE"));
				model.addAttribute("message", "오류가 발생했습니다.");
			}else {
				model.addAttribute("result", true);
				model.addAttribute("data", ciRepJenJobList);
			}
		} 
		catch(Exception ex){
			model.addAttribute("result", false);
			model.addAttribute("errorCode", OslErrorCode.SERVER_ERROR);
			model.addAttribute("message", "오류가 발생했습니다.");
			Log.error("insertCIRepJenJob()", ex);
		}
		return new ModelAndView("jsonView");
	}
}
