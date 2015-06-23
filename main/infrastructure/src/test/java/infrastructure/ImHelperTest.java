package infrastructure;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;



import org.springframework.util.Assert;

import com.divx.service.Util;
import com.divx.service.im.HXImHelper;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.im.HxGroup;
import com.divx.service.model.im.HxResponse;
import com.divx.service.model.im.HxUser;
import com.divx.service.model.im.HxUserResponse;
import com.google.gson.reflect.TypeToken;

public class ImHelperTest {

	@Test
	public void testUserApi() {

		HxUser user = new HxUser();
		user.setUsername("tytytest_xx");
		user.setPassword("2_tytytest");
		user.setNickname("测试用户2");
		HxUserResponse<List<HxUser>> res = HXImHelper.RegisterUser(user);
		//List<?> objs = res.getEntities();
		
		//Assert.isTrue(res.getResponseCode() == 0);
		
		HxUserResponse<List<HxUser>> allUsers = HXImHelper.GetUsers(20, "");
		if (allUsers.getResponseCode() == ResponseCode.SUCCESS)
		{
			for (HxUser u: allUsers.getEntities())
			{
				ServiceResponse dRes = HXImHelper.DeleteUser(u.getUsername());
				Assert.isTrue(dRes.getResponseCode() == ResponseCode.SUCCESS);
			}
		}
		allUsers = HXImHelper.GetUsers(20, "");
		Assert.isTrue(allUsers.getCount() == 0 || allUsers.getEntities() == null || allUsers.getEntities().size() == 0);
	}

	//@Test
	public void testGroupApi()
	{
		HxGroup group = new HxGroup();
		group.setGroupname("testGroup");
		group.setDesc("this is a test");
		group.setMaxusers(200);
		group.setOwner("tytytest_1");
		group.setApproval(true);
		group.setPublic(false);
		
		HxResponse<HxGroup> res = HXImHelper.CreateGroup(group);
		Assert.isTrue(res.getResponseCode() == ResponseCode.SUCCESS);
		if (res.getResponseCode() == ResponseCode.SUCCESS)
		{
		//	String groupId = res.getData().getGroupid();
		//	ServiceResponse dres = HXImHelper.DeleteGroup(groupId);
		//	Assert.isTrue(dres.getResponseCode() == ResponseCode.SUCCESS);
		}
		
		HxResponse<List<HxGroup>> allGroups = HXImHelper.GetAllGroups();
		if (allGroups.getResponseCode() == ResponseCode.SUCCESS)
		{
			for(HxGroup g: allGroups.getData())
			{
				String groupId = g.getGroupid();
				ServiceResponse dres = HXImHelper.DeleteGroup(groupId);
				Assert.isTrue(dres.getResponseCode() == ResponseCode.SUCCESS);
			}
		}
		
		allGroups = HXImHelper.GetAllGroups();
		Assert.isTrue(allGroups.getData() == null || allGroups.getData().size() == 0);
	}
	
	//@Test
	public void testJsonToGroup()
	{
		String jsonStr = "{\"action\":\"post\",\"application\":\"30cc9560-0daf-11e5-a1f0-b9336bc75025\",\"uri\":\"https://a1.easemob.com/iiiview/tyty\",\"entities\":[],\"data\":{\"groupid\":\"1433988710712781\"},\"timestamp\":1433988710703,\"duration\":30,\"organization\":\"iiiview\",\"applicationName\":\"tyty\",\"statusCode\":200}";
		
		HxResponse<HxGroup> res = Util.JsonToObject(jsonStr, new HxResponse<HxGroup>().getClass());
		
		HxResponse<HxGroup> sample = new HxResponse<HxGroup>();
		sample.setAction("post");
		sample.setApplicatiion("30cc9560-0daf-11e5-a1f0-b9336bc75025");
		sample.setUri("https://a1.easemob.com/iiiview/tyty");
		sample.setTimestamp("1433988710703");
		sample.setDuration(300);
		
		HxGroup data = new HxGroup();
		data.setGroupid("1433988710712781");
		sample.setData(data);
		
		String myjson = Util.ObjectToJson(sample);
		
		HxResponse<HxGroup> res1 = Util.JsonToObject(myjson, new TypeToken<HxResponse<HxGroup>>(){}.getType());
		try
		{
			HxGroup data1 = res1.getData();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		System.out.println(myjson);
	}
}
