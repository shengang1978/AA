package com.divx.service.domain.manager;

import com.divx.service.im.HXImHelper;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.im.HxUser;

public class ImHelper {
	private static boolean enableIm = false;
	public static void RegisterUser(int userId, String nickname)
	{
		if (!enableIm) return;
		String username = String.format("tyty_%d", userId);
		String password = String.format("%d_tyty", userId);
		ServiceResponse hxRes = HXImHelper.RegisterUser(new HxUser(username, 
					password,
					nickname));
	}
	
	public static void UpdateNickname(int userId, String nickname)
	{
		if (!enableIm) return;
		String username = String.format("tyty_%d", userId);
		ServiceResponse hxRes = HXImHelper.UpdateNickname(new HxUser(username, 
					"",
					nickname));
	}
	
	public static void DeleteUser(int userId)
	{
		if (!enableIm) return;
		String username = String.format("tyty_%d", userId);
		ServiceResponse hxRes = HXImHelper.DeleteUser(username);
	}
}
