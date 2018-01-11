package com.xunge.service.twrrent.settlement.excelverifyentity;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHanlderResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.util.StrUtil;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;

/**
 * @description 导入导出验证数据完整性
 * @author zhujj
 * @date 2017年7月18日 上午9:37:56 
 * @version 1.0.0 
 */
public class TowerBillbalanceVerify implements IExcelVerifyHandler<TowerBillbalanceVO> {

	@Override
	public ExcelVerifyHanlderResult verifyHandler(TowerBillbalanceVO obj) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
        if (StrUtil.isNotEmpty(obj.getAccountPeroid())) {
            builder.append(PromptMessageComm.PAYMENT_MOUNTH_ISNOT_NULL);
        }
        return new ExcelVerifyHanlderResult(false, builder.toString());
	}

	

}
