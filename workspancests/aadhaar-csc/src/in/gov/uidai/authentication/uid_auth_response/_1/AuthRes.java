package in.gov.uidai.authentication.uid_auth_response._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "", name = "AuthRes")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class AuthRes {
	@XmlAttribute(name = "ret")
	protected String ret;
	@XmlAttribute(name = "code")
	protected String code;
	@XmlAttribute(name = "txn")
	protected String txn;
	@XmlAttribute(name = "err")
	protected String err;
	@XmlAttribute(name = "info")
	protected String info;
	@XmlAttribute(name = "ts")
	protected String ts;
	@XmlAttribute(name = "action")
	protected String action;

	public String getRet() {
		return this.ret;
	}

	public void setRet(String value) {
		this.ret = value;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String value) {
		this.code = value;
	}

	public String getTxn() {
		return this.txn;
	}

	public void setTxn(String value) {
		this.txn = value;
	}

	public String getErr() {
		return this.err;
	}

	public void setErr(String value) {
		this.err = value;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String value) {
		this.info = value;
	}

	public String getTs() {
		return this.ts;
	}

	public void setTs(String value) {
		this.ts = value;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}