package com.xhgx.web.admin.message.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.message.dao.MessageDao;
import com.xhgx.web.admin.message.entity.Message;
import com.xhgx.web.admin.message.service.MessageService;
import com.xhgx.web.admin.msgUser.dao.MsgUserDao;
import com.xhgx.web.admin.msgUser.entity.MsgUser;
import com.xhgx.web.base.websocket.SocketMsg;
import com.xhgx.web.base.websocket.WebEventPusher;

 /**
 * @ClassName MessageServiceImpl
 * @Description tb_sys_message 业务逻辑层实现
 * @author <font color='blue'>ryh</font> 
 * @date 2017-11-08 15:10:05
 * @version 1.0
 */
@Transactional
@Component("messageService") 
public class MessageServiceImpl extends  BatisBaseServiceImpl implements MessageService{
	
	static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	private final static String CACHE_LIST = "message.cache.list";
	private final static String CACHE_MAP = "message.cache.map";
		
	@Override
    public GenericDao<Message, String> getGenericDao() {
        return messageDao;
    }
		
	
	@Autowired
	@Qualifier("messageDao")
	private MessageDao messageDao;
	
	@Autowired
	private MsgUserDao msgUserDao;
	
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.zohan.sdk.framework.service.EntityService#init()
	 */
	@Override
	public void initCache() {
		log.info("开始初始化tb_sys_message数据");
		Map<String,Object> param  = new HashMap();		
		List<Message> list = this.findList(param,null);
		SimpleCacheHelper.put(CACHE_LIST, list);
		Map<String, Message> map = new ConcurrentHashMap<String, Message>();
		for (Message entity : list) {
			map.put(entity.getId(), entity);
		}
		SimpleCacheHelper.put(CACHE_MAP, map);
		log.info("始化tb_sys_message数据完毕，共缓存"+list.size()+"条数据");
	}
	
	
	
	@Override
	 public  void reloadCache(){
		 //TODO 这里是重新加载缓存的方法
		 //initCache();
	 }
	
	
	
	
	
	
	
	/**
	 *  (non-Javadoc)
	 * @see IMessageService#getMessage(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Message getMessage(String messageId){
		Map<String, Message> map  = (Map<String, Message>) SimpleCacheHelper.get(CACHE_MAP);
		Message message = null;
		if(map!=null){
			message = map.get(messageId);
		}else{
			map = new ConcurrentHashMap<String, Message>();
		}
		if(message == null){
			message = (Message)messageDao.get(messageId);
			map.put(message.getId(), message);
			SimpleCacheHelper.put(CACHE_MAP, map);
		}
		return message;
	}

	/**
	 * 根据消息id查询所有消息接收者的用户信息
	*/
	@Override
	public String getUsersByMsgId(String id) {
		
		List<HashMap> list=messageDao.getUsersByMsgId(id);
		String users ="";
		for (HashMap hashMap : list) {
			users+=hashMap.get("REALNAME")+";";
		}
//		users=users.substring(0, users.length()-1);
		return users;
	}


	/**
	 * 根据用户id查询所有接收到的信息
	*/
	@Override
	public List<Message> getMsgByUserId(Map<String, Object> params) {
		List<HashMap> list = null;
		list = messageDao.getMsgByUserId(params);
		List<Message> listMsg= new ArrayList<Message>();
		for (HashMap hashMap : list) {
			Message msg = new Message();
			msg.setId((String) hashMap.get("ID"));
			msg.setTitle((String) hashMap.get("TITLE"));
			msg.setType(hashMap.get("TYPE")+"");
			msg.setCreateBy((String) hashMap.get("CREATEBY"));
			msg.setContent((String) hashMap.get("CONTENT"));
			msg.setCreateTime((Date) hashMap.get("CREATETIME"));
			msg.setStatus(hashMap.get("STATUS")+"");
			listMsg.add(msg);
		}
		return listMsg;
	}


	/**
	 * 根据用户id查询所有未读信息的数量
	*/
	@Override
	public int countUnMsgByUserId(String userId) {
		return messageDao.countUnMsgByUserId(userId);
	}


	/**
	 * 根据用户id条件查询所有接收到的信息的数量
	*/
	@Override
	public int countMsgByUserId(Map<String, Object> params) {
		return messageDao.countMsgByUserId(params);
	}



	@Override
	public void saveAndPublish(Message msg, String[] ids) {
		messageDao.save(msg);
		for (String id : ids) {
			MsgUser msgUser= new MsgUser();
			msgUser.setMsgId(msg.getId());
			msgUser.setUserId(id);
			msgUser.setStatus("0");
			msgUserDao.save(msgUser);
			/*SocketMsg socketMsg = new SocketMsg();
	        socketMsg.setEvent("E01_002");
	        socketMsg.setData(msg);
	        System.out.println(msg);
	        WebEventPusher.pushWebEventToAll(socketMsg);*/
		}
	}


	/**删除消息时，消息用户表中对应的数据也需要被删除*/
	@Override
	public void deleteAndPublish(Message msg) {
		messageDao.delete(msg);
		msgUserDao.deleteByMsgId(msg.getId());
		
	}

	@Override
	public void deleteBatchAndPublish(String[] ids) {
		messageDao.deleteBatch(ids);
		for (String id : ids) {
			msgUserDao.deleteByMsgId(id);
		}
		
	}



	@Override
	public void updateAndPublish(Message msg, String[] ids) {
		msgUserDao.deleteByMsgId(msg.getId());
		for (String id : ids) {
			MsgUser msgUser= new MsgUser();
			msgUser.setMsgId(msg.getId());
			msgUser.setUserId(id);
			msgUser.setStatus("0");
			msgUserDao.save(msgUser);
		}
		
	}
}