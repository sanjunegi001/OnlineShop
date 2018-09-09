package in.gov.uidai.authentication.uid_auth_request._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "RcFlag")
@XmlEnum
public enum RcFlag {

	Y("Y"), N("N");

	private final String value;

	private RcFlag(String v) {
		this.value = v;
	}

	public final String value() {
		return this.value;
	}

	public static RcFlag fromValue(String v) {
		RcFlag[] arrayOfRcFlag;
		int j = (arrayOfRcFlag = values()).length;
		for (int i = 0; i < j; ++i) {
			RcFlag localRcFlag;
			if ((localRcFlag = arrayOfRcFlag[i]).value.equals(v))
				return localRcFlag;
		}
		throw new IllegalArgumentException(v);
	}
}