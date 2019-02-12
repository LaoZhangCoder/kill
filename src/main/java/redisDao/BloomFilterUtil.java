package redisDao;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomFilterUtil {

	private static BloomFilter<Long> bloomFilter=BloomFilter.create(Funnels.longFunnel(), 1000);
	public static void putid(long seckillid) {
		bloomFilter.put(seckillid);
	}
	public static boolean  iscontain(long seckilld) {
		boolean mightContain = bloomFilter.mightContain(seckilld);
		return mightContain;
		
	}

}
