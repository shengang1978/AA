package com.divx.service.domain.repository.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.ConfigurationManager;
import com.divx.service.domain.model.DcpMessage;
import com.divx.service.domain.model.DcpMessageRecver;
import com.divx.service.domain.model.DcpRecver;
import com.divx.service.domain.model.DcpSender;
import com.divx.service.domain.repository.MessageDao;
import com.divx.service.model.KeyValueFourPair;

@Repository
public class MessageDaoImpl extends BaseDao implements MessageDao {

	@Override
	public List<DcpMessage> GetUnsendMessages(String deviceGuid) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		List<DcpMessage> msgs = null;
		try
		{
			trans = ss.beginTransaction();

			String hql = String.format("select distinct m.* from dcp_message m left join dcp_message_recver mr on m.message_id = mr.message_id left join dcp_recver r on mr.user_id = r.user_id left join dcp_userdevice ud on ud.user_id = r.user_id where ((ud.deviceGuid = '%s'and mr.status = 0 and m.deleted = 0 and m.status = 0 ) or (mr.status = 0  and m.issysmessage = 1 and m.messagetype = 0 and deleted = 0));", 
										deviceGuid);

			msgs = ss.createSQLQuery(hql).addEntity(DcpMessage.class).list();

			trans.commit();
		
			return msgs;
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}		
	}

	@Override
	public int AddMessage(DcpMessage msg, List<Integer> userIds) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.save(msg);
			trans.commit();

			int msgId = msg.getMessageId();

			if (userIds != null && userIds.size() > 0)
			{
				trans = ss.beginTransaction();
				for (int id: userIds)
				{
					DcpMessageRecver mr = new DcpMessageRecver();
					mr.setDatecreated(new Date());
					mr.setMessageId(msgId);
					mr.setStatus(0);
					mr.setUserId(id);
					mr.setIosStatus(0);
					mr.setIosAttemps(0);
					mr.setAndroidStatus(0);
					mr.setAndroidAttemps(0);
					ss.save(mr);
				}
				
				trans.commit();
			}
						
			return msgId;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			//e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int SetMessageSended(List<Integer> msgs, int userId) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			
			for(int id : msgs)
			{
				String hql = String.format("UPDATE DcpMessageRecver SET status = 1 WHERE userId = %d AND messageId = %d", userId, id);
				if (ss.createQuery(hql).executeUpdate() == 0)
				{
					DcpMessageRecver mr = new DcpMessageRecver();
					mr.setDatecreated(new Date());
					mr.setMessageId(id);
					mr.setUserId(userId);
					mr.setStatus(1);
					ss.save(mr);
				}
			}
			
			trans.commit();
		
			return 0;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			//e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public List<DcpMessage> GetUnsendMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DcpSender GetSender(int nType, int linkId) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		DcpSender obj = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM DcpSender r WHERE r.linkId = %d and r.type = %d", linkId, nType);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				obj = (DcpSender)objs.get(0);
			}
			trans.commit();
		
			return obj;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}		
	}

	@Override
	public int AddSender(DcpSender obj) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.save(obj);
			trans.commit();
		
			return obj.getSenderId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}	
	}

	@Override
	public int UpdateSender(DcpSender obj) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.update(obj);
			trans.commit();
		
			return obj.getSenderId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}	
	}

	
	@Override
	public  List<KeyValueFourPair<Integer,Integer,String,String>> GetUnsentMessages(int deviceType, int tasksize) {
		Session session = this.getSessionFactory().openSession();
		Transaction trans = null;
		List<KeyValueFourPair<Integer,Integer,String,String>> lists = new LinkedList<KeyValueFourPair<Integer,Integer,String,String>>();
		try
		{
			String hql = "";
			if (deviceType == 1)
			{
				hql = String.format("select b.message_id,b.message_recver_id,a.content,c.deviceuniqueid from dcp_message as a join dcp_message_recver as b on a.message_id = b.message_id join dcp_userdevice as c on b.user_id = c.user_id where b.ios_status = 0 and c.devicetype = 1 and ios_attemps < 5 limit %d", tasksize);
			}
			else if (deviceType == 0)
			{
				hql = String.format("select b.message_id,b.message_recver_id,a.content,c.deviceuniqueid from dcp_message as a join dcp_message_recver as b on a.message_id = b.message_id join dcp_userdevice as c on b.user_id = c.user_id where b.android_status = 0 and c.devicetype = 0 and b.android_attemps < 5 limit %d", tasksize);
			}
			else
			{
				//Should not come here.
				return null;
			}
			trans = session.beginTransaction();
			List<?> objs = session.createSQLQuery(hql).list();
			
			if (objs != null && objs.size() > 0)
			{
				for(Object obj : objs){
					Object[] ary = (Object[])obj;
					KeyValueFourPair<Integer,Integer,String,String> list = new KeyValueFourPair<Integer,Integer,String,String>((Integer)ary[0], (Integer)ary[1], (String)ary[2],(String)ary[3]);
					lists.add(list);						
				}				
			}
	
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			trans.commit();
			session.close();
		}	
		return lists;
	}

	@Override
	public int UpdateMessageStatus(int deviceType, List<Integer> messagesRecverIds,
			boolean resultStatus) {
		Session session = this.getSessionFactory().openSession();
		Transaction trans = null;

		try {
			String hql = "";
			trans = session.beginTransaction();
			
			
			
				if (resultStatus) {
					if (deviceType == 1)
						hql = String.format("UPDATE DcpMessageRecver SET iosStatus = 1 WHERE  messageRecverId IN :messagesRecverIds");
					else {
						hql = String.format("UPDATE DcpMessageRecver SET androidStatus = 1 WHERE messageRecverId IN :messagesRecverIds");
					}
				} else {
					if (deviceType == 1)
						hql = String.format("UPDATE DcpMessageRecver SET iosAttemps = iosAttemps + 1 WHERE  messageRecverId IN :messagesRecverIds");
					else {
						hql = String.format("UPDATE DcpMessageRecver SET androidAttemps = androidAttemps + 1 WHERE messageRecverId IN :messagesRecverIds");
					}
				}
			
			return session.createQuery(hql).setParameterList("messagesRecverIds", messagesRecverIds).executeUpdate();

		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			trans.commit();
			session.close();
		}

	}

}
