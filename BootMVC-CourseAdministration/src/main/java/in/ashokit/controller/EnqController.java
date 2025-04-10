package in.ashokit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.appconfig.AppConfig;
import in.ashokit.bindings.DashboardResp;
import in.ashokit.bindings.EnquiryForm;
import in.ashokit.bindings.EnquirySearchCriteria;
import in.ashokit.constants.View;
import in.ashokit.entity.StudentEnqEntity;
import in.ashokit.service.EnquiryService;
import in.ashokit.service.IEnquiryService;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnqController {
    private final EnquiryService enquiryService;
    @Autowired
    private AppConfig app;
	@Autowired
	private HttpSession session;
	@Autowired
	private IEnquiryService enqServ;
	@Autowired
	private IEnquiryService enqService;

    EnqController(EnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }
	@GetMapping("/dashboard")
	public String dashboardPage(Map<String,Object> map) {
		Integer userId=(Integer)session.getAttribute("userid");
		DashboardResp dbr=enqServ.getDashboardData(userId);
		map.put("dbr", dbr);
		return View.DASH_BOARD;
	}
	@GetMapping("/addenquiry")
	public String addEnquiryPage(@ModelAttribute("enqForm") EnquiryForm form ,Map<String,Object> map) {
		
		map.put("courses", enqService.getCourseNames());
		map.put("status", enqService.getEnqStatus());
		
		
		return "addenquiry";
	}
	@PostMapping("/addenquiry")
	public String addEnquiry(@ModelAttribute("enqForm") EnquiryForm form ,Map<String,Object> map) {
		
	Boolean status=enqService.addEnquiry(form);
		if(status) {
			map.put("success", app.getMessages().get("regSuccess"));
		}
		else {
			map.put("fail", app.getMessages().get("regFail"));
		}
		return "redirect:/dashboard";
	}
	@GetMapping("/enquires")
	public String viewEnquireiesPage(Map<String,Object> map) {
		map.put("courses", enqService.getCourseNames());
		map.put("status", enqService.getEnqStatus());
		Integer userId=(Integer)session.getAttribute("userid");
		List<StudentEnqEntity> list=enquiryService.getEnquiresList(userId);
		map.put("list", list);
		return "viewenquiries";
	}
	@GetMapping("/filtered-enquires")
	public String viewEnquireies(@RequestParam String cname,@RequestParam String sname,@RequestParam String mname,Map<String,Object> map) {
		EnquirySearchCriteria filter=new EnquirySearchCriteria();
		filter.setCourse(cname);filter.setStatus(sname);filter.setMode(mname);
		Integer userId=(Integer)session.getAttribute("userid");
		List<StudentEnqEntity> list=enqServ.getFilteredEnquires(userId, filter);
		map.put("list", list);
		return "filteredenquires";
	}
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
	@GetMapping("/edit/{Id}")
	public String editEnquiry(@RequestParam("Id") String stdId,@ModelAttribute("editform") EnquiryForm form,Map<String,Object> map) {
		map.put("courses", enqService.getCourseNames());
		map.put("status", enqService.getEnqStatus());
		Integer userId=(Integer)session.getAttribute("userid");
		
		return "addenquiry";
	}
}
