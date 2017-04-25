package com.nju.pams.web.controller.bar.asset;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nju.pams.biz.model.vo.ConsumptionOverallVO;
import com.nju.pams.biz.model.vo.DepositOverallVO;
import com.nju.pams.biz.model.vo.FixedAssetOverallVO;
import com.nju.pams.biz.model.vo.LoanOverallVO;
import com.nju.pams.biz.model.vo.RegularIncomeOverallVO;
import com.nju.pams.biz.service.PamsAccountService;
import com.nju.pams.biz.service.PamsDepositService;
import com.nju.pams.biz.service.PamsFixedAssetService;
import com.nju.pams.biz.service.PamsLoanService;
import com.nju.pams.biz.service.PamsRegularIncomeService;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.DateUtil;  
  
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
    
    @Autowired
    private PamsDepositService pamsDepositService;
    
    @Autowired
    private PamsLoanService pamsLoanService;
    
    //返回添加消费账目页面
    @RequestMapping(value = "overall")
    public String getAssetOverallPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	//计算消费的概要
    	ConsumptionOverallVO consumptionOverall = pamsAccountService.getConsumptionOverall(userId);
    	//计算常规收入的概要
    	RegularIncomeOverallVO regularIncomeOverall = pamsRegularIncomeService.getRegularIncomeOverallVO(userId);
    	//计算固定资产的概要
    	FixedAssetOverallVO fixedAssetOverall = pamsFixedAssetService.getFixedAssetOverall(userId);
    	//计算存款的概要
    	DepositOverallVO depositOverall = pamsDepositService.getDepositOverall(userId);
    	//计算贷款的概要
    	LoanOverallVO loanOverall = pamsLoanService.getLoanOverall(userId);
    	
    	model.addAttribute("consumptionOverall", consumptionOverall);
    	model.addAttribute("regularIncomeOverall", regularIncomeOverall);
    	model.addAttribute("fixedAssetOverall", fixedAssetOverall);
    	model.addAttribute("depositOverall", depositOverall);
    	model.addAttribute("loanOverall", loanOverall);
    	
        return "authc/asset-bar/assetOverall";
    }
    
    
}  