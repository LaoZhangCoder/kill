package mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Seckill;

public interface SeckillDao {
    /* *
     *减库存
     * @param seckillId
     * @param killTime
     * @return如果影响行数> 1，表示更新的行数
     */
	int reduceitemnum(@Param("seckillId") long seckillId,@Param("killtime") Date killtime);
	 /**
     * 根据ID查询秒杀对象
     * @param seckillId
     * @return
     */
	Seckill querybyid(long seckillId);

    /**
     * 根据偏移量来查询秒杀商品列表
     * @param offet
     * @param limit
     * @return
     */
	List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
}
