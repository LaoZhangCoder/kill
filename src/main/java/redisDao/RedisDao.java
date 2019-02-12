package redisDao;
import java.util.Random;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
private final JedisPool jedispool;
public RedisDao(String ip,int port) {
	super();
	this.jedispool = new JedisPool(ip, port);
}
private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
public Seckill getSeckill(long seckillId) {
	Jedis resource = jedispool.getResource();
	try {
		
		if(resource!=null) {
			  String key ="seckill_id:"+seckillId;
			  byte[] bs = resource.get(key.getBytes());
			  if(bs!=null) {
				 Seckill newMessage = schema.newMessage();
				 ProtostuffIOUtil.mergeFrom(bs, newMessage, schema);
				 return newMessage;
			  }
		}
	} catch (Exception e) {
		// TODO: handle exception
	}finally {
		resource.close();
	}
	return null;
}
public String putSeckill(Seckill seckill) {
	Jedis resource = jedispool.getResource();
	try {
		  String key = "seckill_id:"+seckill.getSeckillId();
          byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
          //超时缓存，设置一个随机时间，防止缓存雪崩
          Random random = new Random();
          int i = random.nextInt(100);
          int timeout = (int) (60 * 60*i*0.1);
          String result = null;
          //防止缓存击穿
		if (resource.setnx("key_mutex", "1") == 1) {
              resource.expire("key_mutex", 3 * 60);       
      result = resource.setex(key.getBytes(), timeout, bytes);
          
          resource.del("key_mutex"); 
          }else {
        	//其他线程休息50毫秒后重试    
              try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
              putSeckill(seckill);
          }
          return result;
	} finally {
		// TODO: handle finally clause
		resource.close();
	}
	
}
}
