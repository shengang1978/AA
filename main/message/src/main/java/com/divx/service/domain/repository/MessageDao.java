package com.divx.service.domain.repository;

import java.util.List;

import com.divx.service.domain.model.*;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueFourPair;

public interface MessageDao {

	List<DcpMessage> GetUnsendMessages(String deviceGuid);
	
	int AddMessage(DcpMessage msg, List<Integer> userIds);
	
	int SetMessageSended(List<Integer> msgs, int userId);
	
	List<DcpMessage> GetUnsendMessages();
	
	DcpSender GetSender(int nType, int linkId);
	int AddSender(DcpSender obj);
	int UpdateSender(DcpSender obj);
	
	List<KeyValueFourPair<Integer,Integer,String,String>> GetUnsentMessages(int deviceType, int tasksize);
	
	int UpdateMessageStatus(int deviceType, List<Integer> messagesRecverIds, boolean resultStatus);
	
}
