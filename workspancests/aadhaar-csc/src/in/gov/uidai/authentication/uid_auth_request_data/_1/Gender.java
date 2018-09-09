package in.gov.uidai.authentication.uid_auth_request_data._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "gender")
@XmlEnum
public enum Gender {
	M, F, T;

	public final String value() {
		return this.name();
	}

	public static Gender fromValue(String v) {
		return valueOf(v);
	}
}