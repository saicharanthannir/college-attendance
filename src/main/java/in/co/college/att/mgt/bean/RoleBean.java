package in.co.college.att.mgt.bean;



public class RoleBean extends BaseBean {
	
	public static final int ADMIN = 1;
	public static final int FACULTY = 2;
	public static final int STUDENT = 3;


	

	private String name;

	private String description;


	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return name;
	}
}
