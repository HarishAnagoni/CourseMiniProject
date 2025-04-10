package in.ashokit.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.appconfig.AppConfig;
import in.ashokit.bindings.LoginForm;
import in.ashokit.bindings.SignupForm;
import in.ashokit.bindings.UnlockForm;
import in.ashokit.constants.User;
import in.ashokit.entity.UserEntity;
import in.ashokit.repository.IUserRepo;
import in.ashokit.utils.EmailUtils;
import in.ashokit.utils.PwdUtils;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private AppConfig app;
	@Autowired
	private HttpSession session;
	@Autowired
	private IUserRepo userRepo;
	@Autowired
	private EmailUtils email;
	@Override
	public boolean signupUser(SignupForm form) {
		UserEntity em =userRepo.findByEmail(form.getEmail());
		if(em!=null) {
			return false;
		}
		UserEntity entity=new UserEntity();
		BeanUtils.copyProperties(form, entity);
		//password generation
		String tempPwd=PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);
		//set status locked
		entity.setAccStatus(User.LOCKED);
		//insert
		userRepo.save(entity);		
		//email
		String to=form.getEmail();
		String sub=app.getMessages().get("subUnlock");
		StringBuffer body=new StringBuffer();
		body.append("<h1> Use below temparory pwd to unlock your account<h1>");
		body.append("Temporary password is :"+tempPwd);body.append("<br/>");
		body.append("<a href=\"http://localhost:8081/unlock?email="+to+"\">Click to unlock account</a> <br/>");
		email.sendEmail(to, sub, body.toString());
		return true;
	}

	@Override
	public String loginUser(LoginForm form) {
		UserEntity entity=userRepo.findByEmailAndPwd(form.getEmail(),form.getPwd());
		
		if(entity==null) {
			return app.getMessages().get("invalidUser");
		}
		if(entity.getAccStatus().equalsIgnoreCase(User.LOCKED)) {
			return app.getMessages().get("Your account locked,");
		}
		 session.setAttribute("userid", entity.getUserId());
		return "success";
	}

	@Override
	public Boolean forgetPwd(String mail) {
		UserEntity entity=userRepo.findByEmail(mail);
		if(entity==null) {
			return false;
		}else {
			String body="Your Password is ::"+entity.getPwd();
			String sub=app.getMessages().get("subRecover");
			email.sendEmail(mail, sub, body);
			return true;
		}
	}

	@Override
	public String unlockPwd(UnlockForm form) {
		UserEntity entity=userRepo.findByEmail(form.getEmail());
		if(form.getTempPwd().equals(entity.getPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus(User.UNLOCKED);
			userRepo.save(entity);
			return app.getMessages().get("unlockAc");
		}else {
			return app.getMessages().get("wrongtemppzz");
		}
	}

}
