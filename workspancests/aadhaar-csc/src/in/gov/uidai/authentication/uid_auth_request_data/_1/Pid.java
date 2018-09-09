package in.gov.uidai.authentication.uid_auth_request_data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "demo", "bios", "pv" })
@XmlRootElement(name = "Pid")
public class Pid {
	@XmlElement(name = "Demo")
	protected Demo demo;
	@XmlElement(name = "Bios")
	protected Bios bios;
	@XmlElement(name = "Pv")
	protected Pv pv;
	@XmlAttribute(name = "ts", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar ts;
	@XmlAttribute(name = "ver")
	protected String ver;
	@XmlAttribute(name = "wadh")
	protected String wadh;

	public Demo getDemo() {
		return this.demo;
	}

	public void setDemo(Demo value) {
		this.demo = value;
	}

	public Bios getBios() {
		return this.bios;
	}

	public void setBios(Bios value) {
		this.bios = value;
	}

	public Pv getPv() {
		return this.pv;
	}

	public void setPv(Pv value) {
		this.pv = value;
	}

	public XMLGregorianCalendar getTs() {
		return this.ts;
	}

	public void setTs(XMLGregorianCalendar value) {
		this.ts = value;
	}

	public String getVer() {
		return this.ver == null ? "1.0" : this.ver;
	}

	public void setVer(String value) {
		this.ver = value;
	}
}