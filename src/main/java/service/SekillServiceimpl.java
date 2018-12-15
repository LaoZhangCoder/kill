         package service;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import dto.Exposer;
import dto.SeckillExecution;
import dto.SeckillResult;
import entity.Seckill;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import mapper.SeckillDao;
@Service
public class SekillServiceimpl implements SeckillService {
@Autowired
private SeckillDao sd;
	@Override
	public List<Seckill> getSeckillList() {
		// TODO Auto-generated method stub
		List<Seckill> list = sd.queryAll(0, 4);
		return list;
	}

	@Override
	public Seckill getById(long seckillId) {
		// TODO Auto-generated method stub
		Seckill seckill = sd.querybyid(seckillId);
		return seckill;
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		 Seckill seckill = getById(seckillId);

	        //若是秒杀未开启
	        Date startTime = seckill.getStartTime();
	        Date endTime = seckill.getEndTime();
	        //系统当前时间
	        Date nowTime = new Date();
	        if (startTime.getTime() > nowTime.getTime() || endTime.getTime() < nowTime.getTime()) {
	           
	            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
	        }

	        //秒杀开启，返回秒杀商品的id、用给接口加密的md5
	        String md5 = getMD5(seckillId);
	        return new Exposer(true, md5, seckillId);
	    }

	    private String getMD5(long seckillId) {
	        String base = seckillId + "/" + "salt";
	        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
	        return md5;
	    }
	@Override
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeckillResult<Date> getnowtime() {
		// TODO Auto-generated method stub
		Date date = new Date();
		System.out.println(date);
		SeckillResult<Date> result = new SeckillResult<Date>(true,date);
		
		return result;
	}
	

}
