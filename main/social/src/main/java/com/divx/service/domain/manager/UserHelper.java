package com.divx.service.domain.manager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.divx.service.UserServiceHelper;
import com.divx.service.domain.dao.FriendDao;
import com.divx.service.domain.dao.impl.FriendDaoImpl;
import com.divx.service.domain.model.OsfTeamMembers;
import com.divx.service.model.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class UserHelper {
	public User GetUser(int userId)
	{ 
		
		try
		{
			return getUserCallable(userId +"");
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	final Cache<String, User> cache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(10, TimeUnit.MINUTES).build(); 
	public User getUserCallable(final String key) throws ExecutionException {  
        // 没有使用CacheLoader  
      
      
			User resultVal = cache.get(key, new Callable<User>() {
        	@Override
            public User call() {  
        		User user =new User();
        		try{
	        		UserServiceHelper.UserResponse u = new UserServiceHelper().GetUser(Integer.parseInt(key));	        		
	        		if (u != null)
					{						
						user.setUserId(new Long(u.getUser().getUserId()).intValue());
						user.setUsername(u.getUser().getUsername());
						user.setNickname(u.getUser().getNickname());
						user.setPhotoUrl(u.getUser().getPhotourl());
						System.out.println("1----get by User DB: "+ u.getUser()); 
						/*OsfTeamMembers tm = dao.GetUser(Integer.parseInt(key));
							if (tm != null)
							{
								tm.setUsername(u.getUser().getUsername());
								tm.setNickname(u.getUser().getNickname());
								tm.setPhotourl(u.getUser().getPhotourl());
								
								 System.out.println("1----update TeamMember DB: "+tm);  
								 dao.SetUserToGroup(tm);
							}						 
					}else{
						OsfTeamMembers tm = dao.GetUser(Integer.parseInt(key));
						if (tm != null)
						{
						
							user.setUserId(new Long(tm.getUserId()).intValue());
							user.setUsername(tm.getUsername());
							user.setNickname(tm.getNickname());
							user.setPhotoUrl(tm.getPhotourl());
							 System.out.println("1----get by TeamMember DB: "+tm);  
						}*/
					}
	  
        		}catch(Exception ex){
        				ex.printStackTrace();
        		} 
        	return user;
        } 
        });  
        System.out.println("1----get by cache: " + resultVal);  
		return resultVal;  
       
    }  
	private FriendDao dao = new FriendDaoImpl();
}
