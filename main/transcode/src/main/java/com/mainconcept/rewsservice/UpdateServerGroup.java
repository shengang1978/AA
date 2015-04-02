
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
 *         &lt;element name="serverGroup" type="{http://api.rews.mainconcept.com/xsd}ServerGroup" minOccurs="0"/>
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
    "serverGroup"
})
@XmlRootElement(name = "updateServerGroup")
public class UpdateServerGroup {

    @XmlElement(nillable = true)
    protected ServerGroup serverGroup;

    /**
     * 获取serverGroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ServerGroup }
     *     
     */
    public ServerGroup getServerGroup() {
        return serverGroup;
    }

    /**
     * 设置serverGroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ServerGroup }
     *     
     */
    public void setServerGroup(ServerGroup value) {
        this.serverGroup = value;
    }

}
