package in.gov.uidai.authentication.uid_auth_request_data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pfa")
public class Pfa {
	@XmlAttribute(name = "mv")
	protected Integer mv;
	@XmlAttribute(name = "ms")
	protected MatchingStrategy ms;
	@XmlAttribute(name = "av")
	protected String av;
	@XmlAttribute(name = "lmv")
	protected Integer lmv;
	@XmlAttribute(name = "lav")
	protected String lav;

	public int getMv() {
		return this.mv == null ? 100 : this.mv.intValue();
	}

	public void setMv(Integer value) {
		this.mv = value;
	}

	public MatchingStrategy getMs() {
		return this.ms == null ? MatchingStrategy.E : this.ms;
	}

	public void setMs(MatchingStrategy value) {
		this.ms = value;
	}

	public String getAv() {
		return this.av;
	}

	public void setAv(String value) {
		this.av = value;
	}

	public Integer getLmv() {
		return this.lmv;
	}

	public void setLmv(Integer value) {
		this.lmv = value;
	}

	public String getLav() {
		return this.lav;
	}

	public void setLav(String value) {
		this.lav = value;
	}
}