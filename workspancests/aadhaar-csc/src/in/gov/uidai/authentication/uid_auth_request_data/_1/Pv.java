package in.gov.uidai.authentication.uid_auth_request_data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pv")
public class Pv {
	@XmlAttribute(name = "otp")
	protected String otp;
	@XmlAttribute(name = "pin")
	protected String pin;

	public String getOtp() {
		return this.otp;
	}

	public void setOtp(String value) {
		this.otp = value;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String value) {
		this.pin = value;
	}
}