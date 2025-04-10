package in.ashokit.service;

import in.ashokit.bindings.LoginForm;
import in.ashokit.bindings.SignupForm;
import in.ashokit.bindings.UnlockForm;

public interface IUserService {

	public boolean signupUser(SignupForm form);
	public String loginUser(LoginForm form);
	public Boolean forgetPwd(String email);
	public String unlockPwd(UnlockForm form);
}
