package in.ashokit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.appconfig.AppConfig;
import in.ashokit.bindings.LoginForm;
import in.ashokit.bindings.SignupForm;
import in.ashokit.bindings.UnlockForm;
import in.ashokit.constants.View;
import in.ashokit.service.IUserService;

@Controller
public class UserController {
	@Autowired
	private AppConfig app;
	@Autowired
	private IUserService userService;
	
	@GetMapping("/login")
	public String loginPage(@ModelAttribute("form") LoginForm loginform) {
		return View.LOGIN;
	}
	@PostMapping("/login")
	public String loginpost(@ModelAttribute("form") LoginForm loginform,Map<String,Object> map) {
		String status=userService.loginUser(loginform);
		if(status.equals("success")) {
			return "redirect:/dashboard";
		}
		map.put("errMsg", status);
		return View.LOGIN;
	}
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam("email") String mail,@ModelAttribute("unlockform") UnlockForm form,Map<String,Object> map) {
		map.put("email", mail);		
		return View.UNLOCK;
	}
	@PostMapping("/unlock")
	public String unlockPage1(@ModelAttribute("unlockform") UnlockForm form,Map<String,Object> map) {
			System.out.println(form);
			if(form.getNewPwd().equals(form.getNewPwd1())) {
				String msg=userService.unlockPwd(form);
				map.put("Msg", msg);
			}else {
				map.put("Msg", app.getMessages().get("tempPzzNotMatch"));
			}
		return View.UNLOCK;
	}
	@PostMapping("/register")
	public String handleSignUp(@ModelAttribute("form") SignupForm forms,Map<String,Object> map) {
		boolean status=userService.signupUser(forms);
		if(status) {
			map.put("SuccessMsg", app.getMessages().get("mailSent"));
		}else {
			map.put("errMsg", app.getMessages().get("alreadyReg"));			
		}
		return View.SIGN_UP;
	}
	@GetMapping("/register")
	public String signUp(@ModelAttribute("form") SignupForm forms) {
		return View.SIGN_UP;
	}
	@GetMapping("/forgot")
	public String forgotPWDPage() {
		return View.FORGOT;
	}
	@PostMapping("/forgotPwd")
	public String forgotPWD(@RequestParam String email,Map<String,Object> map) {
		boolean msg=userService.forgetPwd(email);
		if(msg) {
			map.put("success", app.getMessages().get("mailSent"));
		}else {
			map.put("fail", app.getMessages().get("invalidEmail"));			
		}
		return View.FORGOT;
	}
}
