package com.nju.pams.web.controller.bar.asset;


import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nju.pams.biz.service.PamsAccountService;
import com.nju.pams.biz.service.PamsFixedAssetService;
import com.nju.pams.biz.service.PamsRegularIncomeService;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.BigDecimalUtil;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_ASSET_GENERAL_ASSET)
public class GeneralAssetController {  
	
    private static final Logger logger = Logger.getLogger(GeneralAssetController.class);
    
    @Autowired
	private PamsAccountService pamsAccountService;
    
    @Autowired
    private PamsRegularIncomeService pamsRegularIncomeService;
    
    @Autowired
    private PamsFixedAssetService pamsFixedAssetService;
    
    //返回添加消费账目页面
    @RequestMapping(value = "overall")
    public String getAssetOverallPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	//计算消费的总花销
    	BigDecimal allConsumptionValue = BigDecimalUtil.generateFormatNumber(pamsAccountService.computeAllConsumptionValue(userId));
    	//计算常规收入的总收入
    	BigDecimal allRegularIncomeValue = BigDecimalUtil.generateFormatNumber(pamsRegularIncomeService.computeAllConsumptionValue(userId));
    	//计算固定资产的总价值
    	BigDecimal allFixedAssetValue = BigDecimalUtil.generateFormatNumber(pamsFixedAssetService.computeAllConsumptionValue(userId));
    	
    	//TODO 计算其他的总价值或者总花销-------------------------------------------------
    	
    	model.addAttribute("allConsumptionValue", allConsumptionValue);
    	model.addAttribute("allRegularIncomeValue", allRegularIncomeValue);
    	model.addAttribute("allFixedAssetValue", allFixedAssetValue);
        return "authc/asset-bar/assetOverall";
    }
    
    
}  