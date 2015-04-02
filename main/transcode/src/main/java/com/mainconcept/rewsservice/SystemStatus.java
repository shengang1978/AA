
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SystemStatus complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SystemStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="memoryStatus" type="{http://monitor.api.rews.mainconcept.com/xsd}MemoryStatus" minOccurs="0"/>
 *         &lt;element name="operatingSystemStatus" type="{http://monitor.api.rews.mainconcept.com/xsd}OperatingSystemStatus" minOccurs="0"/>
 *         &lt;element name="runtimeStatus" type="{http://monitor.api.rews.mainconcept.com/xsd}RuntimeStatus" minOccurs="0"/>
 *         &lt;element name="threadsStatus" type="{http://monitor.api.rews.mainconcept.com/xsd}ThreadsStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemStatus", namespace = "http://monitor.api.rews.mainconcept.com/xsd", propOrder = {
    "memoryStatus",
    "operatingSystemStatus",
    "runtimeStatus",
    "threadsStatus"
})
public class SystemStatus {

    @XmlElement(nillable = true)
    protected MemoryStatus memoryStatus;
    @XmlElement(nillable = true)
    protected OperatingSystemStatus operatingSystemStatus;
    @XmlElement(nillable = true)
    protected RuntimeStatus runtimeStatus;
    @XmlElement(nillable = true)
    protected ThreadsStatus threadsStatus;

    /**
     * 获取memoryStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link MemoryStatus }
     *     
     */
    public MemoryStatus getMemoryStatus() {
        return memoryStatus;
    }

    /**
     * 设置memoryStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link MemoryStatus }
     *     
     */
    public void setMemoryStatus(MemoryStatus value) {
        this.memoryStatus = value;
    }

    /**
     * 获取operatingSystemStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link OperatingSystemStatus }
     *     
     */
    public OperatingSystemStatus getOperatingSystemStatus() {
        return operatingSystemStatus;
    }

    /**
     * 设置operatingSystemStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link OperatingSystemStatus }
     *     
     */
    public void setOperatingSystemStatus(OperatingSystemStatus value) {
        this.operatingSystemStatus = value;
    }

    /**
     * 获取runtimeStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RuntimeStatus }
     *     
     */
    public RuntimeStatus getRuntimeStatus() {
        return runtimeStatus;
    }

    /**
     * 设置runtimeStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RuntimeStatus }
     *     
     */
    public void setRuntimeStatus(RuntimeStatus value) {
        this.runtimeStatus = value;
    }

    /**
     * 获取threadsStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ThreadsStatus }
     *     
     */
    public ThreadsStatus getThreadsStatus() {
        return threadsStatus;
    }

    /**
     * 设置threadsStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ThreadsStatus }
     *     
     */
    public void setThreadsStatus(ThreadsStatus value) {
        this.threadsStatus = value;
    }

}
