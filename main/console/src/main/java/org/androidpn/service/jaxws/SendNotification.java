
package org.androidpn.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.0.1
 * Mon Dec 29 16:42:37 CST 2014
 * Generated source version: 3.0.1
 */

@XmlRootElement(name = "sendNotification", namespace = "http://service.androidpn.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendNotification", namespace = "http://service.androidpn.org/")

public class SendNotification {

    @XmlElement(name = "arg0")
    private org.androidpn.service.model.Notification arg0;

    public org.androidpn.service.model.Notification getArg0() {
        return this.arg0;
    }

    public void setArg0(org.androidpn.service.model.Notification newArg0)  {
        this.arg0 = newArg0;
    }

}

