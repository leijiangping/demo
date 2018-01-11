package audit_job;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.xunge.model.util.SmsSendVO;
import com.xunge.model.util.SmsType;
import com.xunge.service.sms.send.impl.SmsSendTools;

/**
 * Junit Test Send Sms Itf
 * @author Administrator
 *
 */
public class testSmsSend {
	
	@Test
	public void launch(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mobiles","13572457558");
		param.put("tempid","10007");
		
		SmsSendVO sendVo = new SmsSendVO();
		sendVo.setSmsType(SmsType.LoginValidBySms);
		sendVo.setParam(param);
		SmsSendTools.sendTo(sendVo);
	}

}
