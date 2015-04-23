package com.divx.service.model;

public class DcpBaseType {

	public enum eDeviceType
	{
		Android,
		iOS,
		CE
	}
	
	public enum eAppType
	{
		Unknown,		//0. Ref App, Ohana, 漫视等等, 电子画册
		RefApp,			//1. Ref App - DivX DCP
		Manshi,			//2. 漫视
		Dianzihuace,	//3. 电子画册
		Yingyueguan,	//4. 英阅馆
	}
}
