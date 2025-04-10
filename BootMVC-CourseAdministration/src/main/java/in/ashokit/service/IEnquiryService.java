package in.ashokit.service;

import java.util.List;

import in.ashokit.bindings.DashboardResp;
import in.ashokit.bindings.EnquiryForm;
import in.ashokit.bindings.EnquirySearchCriteria;
import in.ashokit.entity.StudentEnqEntity;

public interface IEnquiryService {
	public List<String> getCourseNames();
	public List<String> getEnqStatus();
	public EnquiryForm getEnquiry(Integer enqId);
	
	public DashboardResp getDashboardData(Integer id);
	public Boolean addEnquiry(EnquiryForm form);
	public List<EnquiryForm> getEnquires(Integer id,EnquiryForm form);
	public List<StudentEnqEntity> getEnquiresList(Integer id);

	public List<StudentEnqEntity> getFilteredEnquires(Integer id,EnquirySearchCriteria search);
}