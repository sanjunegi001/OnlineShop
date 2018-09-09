package in.gov.uidai.authentication.uid_auth_request_data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pi")
public class Pi {
	@XmlAttribute(name = "ms")
	protected MatchingStrategy ms;
	@XmlAttribute(name = "mv")
	protected Integer mv;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "lmv")
	protected Integer lmv;
	@XmlAttribute(name = "lname")
	protected String lname;
	@XmlAttribute(name = "gender")
	protected Gender gender;
	@XmlAttribute(name = "dob")
	protected String dob;
	@XmlAttribute(name = "dobt")
	protected String dobt;
	@XmlAttribute(name = "age")
	protected Integer age;
	@XmlAttribute(name = "phone")
	protected String phone;
	@XmlAttribute(name = "email")
	protected String email;

	public MatchingStrategy getMs() {
		return this.ms == null ? MatchingStrategy.E : this.ms;
	}

	public void setMs(MatchingStrategy value) {
		this.ms = value;
	}

	public int getMv() {
		return this.mv == null ? 100 : this.mv.intValue();
	}

	public void setMv(Integer value) {
		this.mv = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public Integer getLmv() {
		return this.lmv;
	}

	public void setLmv(Integer value) {
		this.lmv = value;
	}

	public String getLname() {
		return this.lname;
	}

	public void setLname(String value) {
		this.lname = value;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender value) {
		this.gender = value;
	}

	public String getDob() {
		return this.dob;
	}

	public void setDob(String value) {
		this.dob = value;
	}

	public String getDobt() {
		return this.dobt;
	}

	public void setDobt(String value) {
		this.dobt = value;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer value) {
		this.age = value;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String value) {
		this.phone = value;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String value) {
		this.email = value;
	}
}