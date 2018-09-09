package in.gov.uidai.authentication.uid_auth_request._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for UsesFlag.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="UsesFlag">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="y"/>
 *     &lt;enumeration value="n"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UsesFlag")
@XmlEnum
public enum UsesFlag {

	@XmlEnumValue("y") Y("y"), @XmlEnumValue("n") N("n");
	private final String value;

	UsesFlag(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static UsesFlag fromValue(String v) {
		for (UsesFlag c : UsesFlag.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
