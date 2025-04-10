package in.ashokit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.bindings.DashboardResp;
import in.ashokit.bindings.EnquiryForm;
import in.ashokit.bindings.EnquirySearchCriteria;
import in.ashokit.constants.Mode;
import in.ashokit.entity.StudentEnqEntity;
import in.ashokit.entity.UserEntity;
import in.ashokit.repository.CourseRepo;
import in.ashokit.repository.IEnquireRepo;
import in.ashokit.repository.IStatusRepo;
import in.ashokit.repository.IUserRepo;
import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryService implements IEnquiryService{

	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private IUserRepo userRepo;
	@Autowired
	private IEnquireRepo enqRepo;
	@Autowired
	private IStatusRepo statusRepo;
	@Autowired
	private HttpSession session;
	@Override
	public Boolean addEnquiry(EnquiryForm form) {
		StudentEnqEntity entity=new StudentEnqEntity();
		BeanUtils.copyProperties(form, entity);
		Integer userid=((Integer)session.getAttribute("userid"));
		UserEntity useEnt=userRepo.findById(userid).get();
		entity.setUser(useEnt);
		entity.setCreateDt(LocalDate.now());
		enqRepo.save(entity);
		return true;
	}

	@Override
	public List<String> getCourseNames() {
		// TODO Auto-generated method stub
		List<String> courses=courseRepo.getCourses();
		return courses;
	}

	@Override
	public List<String> getEnqStatus() {
		// TODO Auto-generated method stub
		List<String> status=statusRepo.getStatuses();
		return status;
	}

	@Override
	public EnquiryForm getEnquiry(Integer enqId) {
		
		return null;
	}

	@Override
	public DashboardResp getDashboardData(Integer id) {
		DashboardResp dbr=new DashboardResp();
		Optional<UserEntity> entity =userRepo.findById(id);
		if(entity.isPresent()) {
			UserEntity ent=entity.get();
			List<StudentEnqEntity> list=ent.getEnquires();
			Integer totalEnq=list.size();
			Integer enrolled=list.stream().filter(e->e.getStatus().equalsIgnoreCase(Mode.ENROLLED)).collect(Collectors.toList()).size();
			Integer lost=list.stream().filter(e->e.getStatus().equalsIgnoreCase(Mode.LOST)).collect(Collectors.toList()).size();
			
			dbr.setTotalEnqCount(totalEnq);
			dbr.setEnrolledCount(enrolled);
			dbr.setLostCount(lost);
		}
		return dbr;
	}

	@Override
	public List<EnquiryForm> getEnquires(Integer id, EnquiryForm form) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<StudentEnqEntity> getFilteredEnquires(Integer id, EnquirySearchCriteria search) {
		Optional<UserEntity> opt=userRepo.findById(id);
		if(opt.isPresent()) {
			UserEntity user=opt.get();
			List<StudentEnqEntity> list=user.getEnquires();	
			if(search.getCourse()!=null&&!"".equals(search.getCourse())){
				list=list.stream().filter(x->x.getCourse().equals(search.getCourse())).collect(Collectors.toList());
			}if(search.getStatus()!=null&&!"".equals(search.getStatus())){
				list=list.stream().filter(x->x.getStatus().equals(search.getStatus())).collect(Collectors.toList());
			}if(search.getMode()!=null&&!"".equals(search.getMode())){
				list=list.stream().filter(x->x.getMode().equals(search.getMode())).collect(Collectors.toList());
			}
			
		return list;
		}else {
			throw new IllegalArgumentException("Invalid User");
		}
	}
	@Override
	public List<StudentEnqEntity> getEnquiresList(Integer id) {
		Optional<UserEntity> opt=userRepo.findById(id);
		if(opt.isPresent()) {
			UserEntity user=opt.get();
			List<StudentEnqEntity> list=user.getEnquires();	
		return list;
		}else {
			throw new IllegalArgumentException(Mode.INVALID_USER);
		}
	}
}
