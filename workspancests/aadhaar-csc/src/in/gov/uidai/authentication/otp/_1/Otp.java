package in.gov.uidai.authentication.otp._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Otp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Otp", propOrder = { "opts" })
public class Otp {

	@XmlElement(name = "Opts")
	protected Opts opts;

	@XmlAttribute(name = "uid", required = true)
	protected String uid;

	@XmlAttribute(name = "tid", required = true)
	protected String tid;

	@XmlAttribute(name = "ac", required = true)
	protected String ac;

	@XmlAttribute(name = "sa", required = true)
	protected String sa;

	@XmlAttribute(name = "ver", required = true)
	protected String ver;

	@XmlAttribute(name = "txn", required = true)
	protected String txn;

	@XmlAttribute(name = "lk", required = true)
	protected String lk;

	@XmlAttribute(name = "type", required = false)
	protected String type;

	@XmlAttribute(name = "ts", required = false)
	protected String ts;

	public Opts getOpts() {
		return this.opts;
	}

	public void setOpts(Opts value) {
		this.opts = value;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String value) {
		this.uid = value;
	}

	public String getTid() {
		return this.tid;
	}

	public void setTid(String value) {
		this.tid = value;
	}

	public String getAc() {
		return this.ac;
	}

	public void setAc(String value) {
		this.ac = value;
	}

	public String getSa() {
		return this.sa;
	}

	public void setSa(String value) {
		this.sa = value;
	}

	public String getVer() {
		return this.ver;
	}

	public void setVer(String value) {
		this.ver = value;
	}

	public String getTxn() {
		return this.txn;
	}

	public void setTxn(String value) {
		this.txn = value;
	}

	public String getLk() {
		return this.lk;
	}

	public void setLk(String value) {
		this.lk = value;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTs() {
		return this.ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
}