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
	
	//AppVersion field in the dcp_media table
	// It's bitwise field. 
	public abstract class BookBitwise
	{
		public static final int Normal = 0x0001;
		public static final int AppStore = 0x0002;
		
		public static final int All = 0xFFFF;
	}
}
