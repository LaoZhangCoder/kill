package mapper;

import org.apache.ibatis.annotations.Param;

import entity.SuccessKilled;

public interface SucessSeckillDao {
	/**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的行数，返回0代表插入失败
     */
	public int insertSucessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	 /**
     * 根据id查询SuccesskillId并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByidWithkilld(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
		
}
