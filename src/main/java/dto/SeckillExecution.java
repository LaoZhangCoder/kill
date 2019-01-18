package dto;

import entity.SuccessKilled;
import enums.SeckillStatEnum;

/**
 * 封装秒杀执行后的结果
 *
 * @author colg
 */
public class SeckillExecution  {

	/**
	 * 库存商品id
	 */
	private Long seckillId;

	/**
	 * 商品秒杀状态标识（-1：无效，0：成功，1：已付款）
	 */
	private int state;

	/**
	 * 状态表示
	 */
	private String stateInfo;

	/**
	 * 秒杀成功对象，当秒杀成功时，返回此对象
	 */
	private SuccessKilled successKilled;

	public SeckillExecution() {
	}

	public SeckillExecution(Long seckillId, SeckillStatEnum statEnum) {
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getInfo();
	}

	public SeckillExecution(Long seckillId, SeckillStatEnum statEnum, SuccessKilled successKilled) {
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getInfo();
		this.successKilled = successKilled;
	}

	public Long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

}
