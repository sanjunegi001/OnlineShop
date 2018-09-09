
package in.gov.uidai.authentication.uid_auth_request_data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pa")
public class Pa {
	@XmlAttribute(name = "ms")
	protected MatchingStrategy ms;
	@XmlAttribute(name = "co")
	protected String co;
	@XmlAttribute(name = "house")
	protected String house;
	@XmlAttribute(name = "street")
	protected String street;
	@XmlAttribute(name = "lm")
	protected String lm;
	@XmlAttribute(name = "loc")
	protected String loc;
	@XmlAttribute(name = "vtc")
	protected String vtc;
	@XmlAttribute(name = "po")
	protected String po;
	@XmlAttribute(name = "subdist")
	protected String subdist;
	@XmlAttribute(name = "dist")
	protected String dist;
	@XmlAttribute(name = "state")
	protected String state;
	@XmlAttribute(name = "pc")
	protected String pc;
	@XmlAttribute(name = "country")
	protected String country;

	public MatchingStrategy getMs() {
		return this.ms == null ? MatchingStrategy.E : this.ms;
	}

	public void setMs(MatchingStrategy value) {
		this.ms = value;
	}

	public String getCo() {
		return this.co;
	}

	public void setCo(String value) {
		this.co = value;
	}

	public String getHouse() {
		return this.house;
	}

	public void setHouse(String value) {
		this.house = value;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String value) {
		this.street = value;
	}

	public String getLm() {
		return this.lm;
	}

	public void setLm(String value) {
		this.lm = value;
	}

	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String value) {
		this.loc = value;
	}

	public String getVtc() {
		return this.vtc;
	}

	public void setVtc(String value) {
		this.vtc = value;
	}

	public String getPo() {
		return this.po;
	}

	public void setPo(String value) {
		this.po = value;
	}

	public String getSubdist() {
		return this.subdist;
	}

	public void setSubdist(String value) {
		this.subdist = value;
	}

	public String getDist() {
		return this.dist;
	}

	public void setDist(String value) {
		this.dist = value;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String value) {
		this.state = value;
	}

	public String getPc() {
		return this.pc;
	}

	public void setPc(String value) {
		this.pc = value;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}