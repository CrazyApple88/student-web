package com.xhgx.web.admin.msgUser.service.impl;
import com.xhgx.web.admin.msgUser.dao.MsgUserDao;
import com.xhgx.web.admin.msgUser.service.MsgUserService;
import com.xhgx.web.admin.msgUser.entity.MsgUser;

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

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.sdk.cache.SimpleCacheHelper;

 /**
 * @ClassName MsgUserServiceImpl
 * @Description tb_sys_msg_user 业务逻辑层实现
 * @author <font color='blue'>ryh</font> 
 * @date 2017-11-08 15:10:05
 * @version 1.0
 */
@Transactional
@Component("msgUserService") 
public class MsgUserServiceImpl extends  BatisBaseServiceImpl implements MsgUserService{
	
	static Logger log = LoggerFactory.getLogger(MsgUserServiceImpl.class);
	
	private final static String CACHE_LIST = "msgUser.cache.list";
	private final static String CACHE_MAP = "msgUser.cache.map";
		
	@Override
    public GenericDao<MsgUser, String> getGenericDao() {
        return msgUserDao;
    }
		
	
	@Autowired
	@Qualifier("msgUserDao")
	private MsgUserDao msgUserDao;
	
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.zohan.sdk.framework.service.EntityService#init()
	 */
	@Override
	public void initCache() {
		log.info("开始初始化tb_sys_msg_user数据");
		Map<String,Object> param  = new HashMap();		
		List<MsgUser> list = this.findList(param,null);
		SimpleCacheHelper.put(CACHE_LIST, list);
		Map<String, MsgUser> map = new ConcurrentHashMap<String, MsgUser>();
		for (MsgUser entity : list) {
			map.put(entity.getId(), entity);
		}
		SimpleCacheHelper.put(CACHE_MAP, map);
		log.info("始化tb_sys_msg_user数据完毕，共缓存"+list.size()+"条数据");
	}
	
	
	
	@Override
	 public  void reloadCache(){
		 //TODO 这里是重新加载缓存的方法
		 //initCache();
	 }
	
	
	
	
	
	
	
	/**
	 * (non-Javadoc)
	 * @see IMsgUserService#getMsgUser(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MsgUser getMsgUser(String msgUserId){
		Map<String, MsgUser> map  = (Map<String, MsgUser>) SimpleCacheHelper.get(CACHE_MAP);
		MsgUser msgUser = null;
		if(map!=null){
			msgUser = map.get(msgUserId);
		}else{
			map = new ConcurrentHashMap<String, MsgUser>();
		}
		if(msgUser == null){
			msgUser = (MsgUser)msgUserDao.get(msgUserId);
			map.put(msgUser.getId(), msgUser);
			SimpleCacheHelper.put(CACHE_MAP, map);
		}
		return msgUser;
	}



	@Override
	public void deleteByMsgId(String msgId) {
		msgUserDao.deleteByMsgId(msgId);
		
	}


	/**
	 * 改变消息的状态，从未读改成已读
	 */
	@Override
	public void modifyMsgStatus(MsgUser msgUser) {
		msgUserDao.modifyMsgStatus(msgUser);
	}

	@Override
	public void modifyBatchMsgStatus(String userId, String[] ids) {
		for (String id : ids) {
			MsgUser msgUser=new MsgUser();
			msgUser.setMsgId(id);
			msgUser.setUserId(userId);
			msgUserDao.modifyMsgStatus(msgUser);
		}
		
	}
	
	
		
}