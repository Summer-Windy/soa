package org.soa.rest.resources;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.soa.common.context.SoaContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sys.api.croe.SysSoaManger;

import com.alibaba.dubbo.config.annotation.Reference;

@Controller
@RequestMapping
public class UserResources {
	
	@Reference(version = "1.0.0",interfaceClass=SysSoaManger.class,timeout=2000)
	private SysSoaManger soaManger;

	@ResponseBody
	@RequestMapping(value="/sys/service",method={RequestMethod.GET,RequestMethod.POST})
	public SoaContext login(@ModelAttribute SoaContext context,HttpServletRequest request) {
		final Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			System.out.println("key:"+key+" -------------  value:"+request.getParameter(key));
			
			if(key.intern() == "method".intern() ||key.intern() == "service".intern()) continue;
			context.addAttr(key, request.getParameter(key));
		}
		context = soaManger.callNoTx(context);
		return context;
	}
	
	
	
	@RequestMapping(value="/views/{url}",method={RequestMethod.GET,RequestMethod.POST})
	public String loginView(@PathVariable("url") String url){
		return url;
	}
	

}