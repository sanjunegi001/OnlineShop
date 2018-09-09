package in.gov.uidai.authentication.uid_auth_request_data._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "matchingStrategy")
@XmlEnum
public enum MatchingStrategy {
	E, P, F;

	public final String value() {
		return this.name();
	}

	public static MatchingStrategy fromValue(String v) {
		return valueOf(v);
	}
}