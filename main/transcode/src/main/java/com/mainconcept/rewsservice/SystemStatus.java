
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SystemStatus complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡmemoryStatus���Ե�ֵ��
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
     * ����memoryStatus���Ե�ֵ��
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
     * ��ȡoperatingSystemStatus���Ե�ֵ��
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
     * ����operatingSystemStatus���Ե�ֵ��
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
     * ��ȡruntimeStatus���Ե�ֵ��
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
     * ����runtimeStatus���Ե�ֵ��
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
     * ��ȡthreadsStatus���Ե�ֵ��
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
     * ����threadsStatus���Ե�ֵ��
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
