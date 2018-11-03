package entity;

import java.util.Date;

public class SucessSeckill {
  private long seckill_id;
  private long user_phone;
  private int state;
  private Date create_time;
  private Seckill seckill;
  
  
@Override
public String toString() {
	return "SucessSeckill [seckill_id=" + seckill_id + ", user_phone=" + user_phone + ", state=" + state
			+ ", create_time=" + create_time + ", seckill=" + seckill + "]";
}
public Seckill getSeckill() {
	return seckill;
}
public void setSeckill(Seckill seckill) {
	this.seckill = seckill;
}
public long getSeckill_id() {
	return seckill_id;
}
public void setSeckill_id(long seckill_id) {
	this.seckill_id = seckill_id;
}
public long getUser_phone() {
	return user_phone;
}
public void setUser_phone(long user_phone) {
	this.user_phone = user_phone;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public Date getCreate_time() {
	return create_time;
}
public void setCreate_time(Date create_time) {
	this.create_time = create_time;
}
  
}
