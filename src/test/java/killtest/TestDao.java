package killtest;


import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import entity.Seckill;
import entity.SucessSeckill;
import mapper.SeckillDao;
import mapper.SucessSeckillDao;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class TestDao {

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SucessSeckillDao successKilledDao;
 
    public void insetSuccessKilled() throws Exception {
        long seckillId=1000L;
        long userPhone=13800001111L;
        int insertCount=successKilledDao.insertSucessKilled(seckillId,userPhone);
        System.out.print("insertCount="+insertCount);

    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long seckillId=1000L;
        long userPhone=13800001111L;
        SucessSeckill successKilled=successKilledDao.queryByidWithkilld(seckillId,userPhone);
        System.out.println(successKilled.toString());
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void reduceNumber() throws Exception {
        Date killtime=new Date();
        int updateCount=seckillDao.reduceitemnum(1000l, killtime);
        System.out.print("updateCount = "+updateCount);
    }

   
    public void queryById() throws Exception {
        long id=1000;
        System.out.println(seckillDao);
        Seckill seckill =seckillDao.querybyid(id);
        System.out.print(seckill.getName());
        System.out.print(seckill);
    }
   
    public void queryAll() throws Exception {
        List<Seckill> seckills=seckillDao.queryAll(0,100);
        for (Seckill seckill:seckills) {
            System.out.println(seckill);

        }
    }
}
