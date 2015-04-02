
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="newGroup" type="{http://api.rews.mainconcept.com/xsd}UserGroup" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "newGroup"
})
@XmlRootElement(name = "addUserGroup")
public class AddUserGroup {

    @XmlElement(nillable = true)
    protected UserGroup newGroup;

    /**
     * 获取newGroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link UserGroup }
     *     
     */
    public UserGroup getNewGroup() {
        return newGroup;
    }

    /**
     * 设置newGroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link UserGroup }
     *     
     */
    public void setNewGroup(UserGroup value) {
        this.newGroup = value;
    }

}
