package com.sky.framework.common.id;

public class IdUtils {
	public static final IdUtils ID_UTILS=new IdUtils();
	private long serverId;
	private Id sessionIdUtil;
	private Id uidUtil;
	private Id primaryKeyIdUtil;
	private Id flowIdUtil;
	private SuffixId suffixFlowIdUtil;
	
	private IdUtils() {
		serverId = Long.valueOf(System.getProperty("serverId", "00"));
		sessionIdUtil = new IdImpl(serverId);
		uidUtil = new IdImpl(serverId);
		primaryKeyIdUtil = new IdImpl(serverId);
		flowIdUtil = new IdImpl(serverId);
		suffixFlowIdUtil = new SuffixIdImpl(serverId);
	}
	
	public static IdUtils getInstance() {
		return ID_UTILS;
	}
	
	public long createSessionId() {
		return sessionIdUtil.createId();
	}
	
	public long createUid() {
		return uidUtil.createId();
	}
	
	public long createSuffixFlowId(String suffix) {
		return suffixFlowIdUtil.createId(suffix);
	}

	public long createFlowId() {
		return flowIdUtil.createId();
	}

	public long createPrimaryKeyId() {
		return primaryKeyIdUtil.createId();
	}
}
