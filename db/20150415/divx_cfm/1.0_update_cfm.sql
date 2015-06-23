delete from divx_cfm.dcp_config where `key` = 'Social_Enable_Manshi';
insert into divx_cfm.dcp_config (`key`, `value`, description, datecreate, datemodify, user_id)
	values ('Social_Enable_Manshi', 'true', 'Enable or disable manshi', now(), now(), 0);
	
delete from divx_cfm.dcp_config where `key` = 'CheckWeixinTokenUrl';
insert into divx_cfm.dcp_config (`key`, `value`, description, datecreate, datemodify, user_id)
	values ('CheckWeixinTokenUrl', 'https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s', 'check weixin token', now(), now(), 0);
	
delete from divx_cfm.dcp_config where `key` = 'GetWeixinUserInfoUrl';
insert into divx_cfm.dcp_config (`key`, `value`, description, datecreate, datemodify, user_id)
	values ('GetWeixinUserInfoUrl', 'https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s', 'weixin user info', now(), now(), 0);
	
delete from divx_cfm.dcp_config where `key` = 'GetQQUserInfoUrl';
insert into divx_cfm.dcp_config (`key`, `value`, description, datecreate, datemodify, user_id)
	values ('GetQQUserInfoUrl', 'https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s', 'QQ user info url', now(), now(), 0);
	
delete from divx_cfm.dcp_config where `key` = 'GetOauth_consumer_key';
insert into divx_cfm.dcp_config (`key`, `value`, description, datecreate, datemodify, user_id)
	values ('GetOauth_consumer_key', '1104548778', 'QQ Oauth_consumer_key', now(), now(), 0);
