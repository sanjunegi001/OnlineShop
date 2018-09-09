package in.gov.uidai.authentication.uid_auth_request_data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Demo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Demo", propOrder = { "pi", "pa", "pfa" })
public class Demo {
	@XmlElement(name = "Pi")
	protected Pi pi;
	@XmlElement(name = "Pa")
	protected Pa pa;
	@XmlElement(name = "Pfa")
	protected Pfa pfa;
	@XmlAttribute(name = "lang")
	protected String lang;

	public Pi getPi() {
		return this.pi;
	}

	public void setPi(Pi value) {
		this.pi = value;
	}

	public Pa getPa() {
		return this.pa;
	}

	public void setPa(Pa value) {
		this.pa = value;
	}

	public Pfa getPfa() {
		return this.pfa;
	}

	public void setPfa(Pfa value) {
		this.pfa = value;
	}

	public String getLang() {
		return this.lang == null ? "23" : this.lang;
	}

	public void setLang(String value) {
		this.lang = value;
	}
}