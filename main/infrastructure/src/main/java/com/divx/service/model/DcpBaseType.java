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
		Unknown,		//0. Ref App, Ohana, ���ӵȵ�, ���ӻ���
		RefApp,			//1. Ref App - DivX DCP
		Manshi,			//2. ����
		Dianzihuace,	//3. ���ӻ���
		Yingyueguan,	//4. Ӣ�Ĺ�
	}
}
