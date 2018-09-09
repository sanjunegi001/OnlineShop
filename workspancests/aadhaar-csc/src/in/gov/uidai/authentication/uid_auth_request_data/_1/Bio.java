package in.gov.uidai.authentication.uid_auth_request_data._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bio", propOrder = { "value" })
public class Bio {
	@XmlValue
	protected byte[] value;
	@XmlAttribute(name = "type", required = true)
	protected BioMetricType type;
	@XmlAttribute(name = "posh", required = true)
	protected BiometricPosition posh;
	@XmlAttribute(name = "bs", required = false)
	protected BiometricPosition bs;

	public byte[] getValue() {
		return this.value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	public BioMetricType getType() {
		return this.type;
	}

	public void setType(BioMetricType value) {
		this.type = value;
	}

	public BiometricPosition getPosh() {
		return this.posh;
	}

	public void setPosh(BiometricPosition value) {
		this.posh = value;
	}

	public BiometricPosition getBs() {
		return this.bs;
	}

	public void setBs(BiometricPosition bs) {
		this.bs = bs;
	}
}