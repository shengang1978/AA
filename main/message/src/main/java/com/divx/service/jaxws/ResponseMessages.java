
package com.divx.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.0.1
 * Fri Oct 24 14:54:42 CST 2014
 * Generated source version: 3.0.1
 */

@XmlRootElement(name = "ResponseMessages", namespace = "http://service.divx.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseMessages", namespace = "http://service.divx.com/")

public class ResponseMessages {

    @XmlElement(name = "arg0")
    private com.divx.service.model.ResponseOption arg0;

    public com.divx.service.model.ResponseOption getArg0() {
        return this.arg0;
    }

    public void setArg0(com.divx.service.model.ResponseOption newArg0)  {
        this.arg0 = newArg0;
    }

}

