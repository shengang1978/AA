
package com.divx.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.0.1
 * Mon Sep 15 14:53:44 CST 2014
 * Generated source version: 3.0.1
 */

@XmlRootElement(name = "MyFriendRequestsResponse", namespace = "http://service.divx.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MyFriendRequestsResponse", namespace = "http://service.divx.com/")

public class MyFriendRequestsResponse {

    @XmlElement(name = "return")
    private com.divx.service.model.FriendResquests _return;

    public com.divx.service.model.FriendResquests getReturn() {
        return this._return;
    }

    public void setReturn(com.divx.service.model.FriendResquests new_return)  {
        this._return = new_return;
    }

}

