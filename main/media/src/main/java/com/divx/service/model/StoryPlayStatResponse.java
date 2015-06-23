package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "StoryPlayStatResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StoryPlayStatResponse")
@XmlSeeAlso(StoryPlayStat.class)
public class StoryPlayStatResponse extends RankStatResponse<StoryPlayStat> {

}
