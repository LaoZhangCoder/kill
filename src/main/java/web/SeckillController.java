package web;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dto.Exposer;
import dto.SeckillResult;
import entity.Seckill;
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
    	  System.out.println(16546);
    	 Exposer result = ss.exportSeckillUrl(seckillId);
		return result;
    	 
     }
	
}
  