package web;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import dto.Exposer;
import dto.SeckillExecution;
import dto.SeckillResult;
import entity.Seckill;
import enums.SeckillStatEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import service.SeckillService;

@Controller
@RequestMapping(value="/seckill")
public class SeckillController {
	@Autowired
	private SeckillService ss;
     @RequestMapping(value="/list",method=RequestMethod.GET)
	public String getSeckillList(Model model) {
		List<Seckill> list = ss.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}
     @RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
     public String getSeckillDetail(@PathVariable("seckillId") Long seckillId,Model model) {
    	 Seckill seckill = ss.getById(seckillId);
    	 model.addAttribute("seckill", seckill);
    	 return "detail";
     }
     @RequestMapping(value="/time/now",method=RequestMethod.GET)
     @ResponseBody
     public SeckillResult<Date> getnowtime(){
    	 SeckillResult<Date> result = ss.getnowtime();
		return result;
    	 
     }
     @RequestMapping(value="/{seckillId}/exposer",method=RequestMethod.GET)
     @ResponseBody
     public Exposer getexposerurl(@PathVariable("seckillId") Long seckillId)
     {
    	  
    	 Exposer result = ss.exportSeckillUrl(seckillId);
		return result;
    	 
     }
     @RequestMapping(value="/{seckillId}/{md5}/execution", method = RequestMethod.POST,
             produces = {"application/json;charset=UTF-8"})
     @ResponseBody
     public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
             @PathVariable("md5") String md5,
             @CookieValue(value = "userPhone",required = false) Long userPhone)
     {
     if (userPhone==null)
     {
     return new SeckillResult<SeckillExecution>(false,"未注册");
     }
   
     try {
     SeckillExecution execution = ss.executeSeckill(seckillId, userPhone, md5);
     return new SeckillResult<SeckillExecution>(true, execution);
     }catch (RepeatKillException e1)
     {
     SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
     return new SeckillResult<SeckillExecution>(true,execution);
     }catch (SeckillCloseException e2)
     {
     SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.END);
     return new SeckillResult<SeckillExecution>(true,execution);
     }
     catch (Exception e)
     {
     SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
     return new SeckillResult<SeckillExecution>(true,execution);
     }

}


     
	
}
  