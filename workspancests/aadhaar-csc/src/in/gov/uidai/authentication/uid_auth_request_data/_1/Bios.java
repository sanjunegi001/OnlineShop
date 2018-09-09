
package in.gov.uidai.authentication.uid_auth_request_data._1;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bios", propOrder = { "bio" })
public class Bios {
	@XmlAttribute(name = "dih")
	protected String dih;
	@XmlElement(name = "Bio")
	protected List<Bio> bio;

	public String getDih() {
		return this.dih;
	}

	public void setDih(String dih) {
		this.dih = dih;
	}

	public List<Bio> getBio() {
		if (this.bio == null) {
			this.bio = new ArrayList();
		}

		return this.bio;
	}
}