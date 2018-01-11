package com.xunge.util.branch;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.comm.BatchControlComm;
import com.xunge.exception.NoteException;

/**
 * 批量操作数据，新增、修改、删除合同、综资、动环数据
 * @author SongJL
 *
 */
@Service
public class DBBranchUtil {
	
	private int iBatchCount = BatchControlComm.iBatchCount;

	@Resource
    private ContractDBBranchFunc tool;
	@Resource
	private ResDBBranchFunc resTool;
	@Resource
	private RingDBBranchFunc ringTool;
	/**
	 * 控制反转
	 * @param flag
	 * @param list
	 * @throws Exception
	 */
	public String branch(int flag, List<?> list) throws Exception {
		
		int isuccess=0, ifailed=0;
		
		for(int i=0, length = list.size(); i<length; i+=iBatchCount){
			int j=(i+iBatchCount>list.size())?list.size():i+iBatchCount;
			List<?> tmp = list.subList(i, j);
			try {
				switch (flag) {
				case 0: tool.invoke(ContractDBBranchFunc.class.getMethod("insertDataSupplier"), tmp);
					break;
				case 1: tool.invoke(ContractDBBranchFunc.class.getMethod("updateDataSupplier"), tmp);
					break;
				case 2:	tool.invoke(ContractDBBranchFunc.class.getMethod("insertIntodatContract"), tmp);
					break;
				case 3: tool.invoke(ContractDBBranchFunc.class.getMethod("updatedatContract"), tmp);
					break;
				case 4: tool.invoke(ContractDBBranchFunc.class.getMethod("insertIntoElecContract"), tmp);
					break;
				case 5: tool.invoke(ContractDBBranchFunc.class.getMethod("updateElecContract"), tmp);
					break;
				case 6: tool.invoke(ContractDBBranchFunc.class.getMethod("insertIntoRentContract"), tmp);
					break;
				case 7: tool.invoke(ContractDBBranchFunc.class.getMethod("updateRentContract"), tmp);
					break;
				case 8: resTool.invoke(ResDBBranchFunc.class.getMethod("datBasestationBatchInsert"), tmp);
					break;
				case 9: resTool.invoke(ResDBBranchFunc.class.getMethod("datBasestationBatchUpdate"), tmp);
					break;
				case 10: resTool.invoke(ResDBBranchFunc.class.getMethod("datBaseresourceBatchInsert"), tmp);
					break;
				case 11: resTool.invoke(ResDBBranchFunc.class.getMethod("datBaseresourceBatchUpdate"), tmp);
					break;
				case 12: resTool.invoke(ResDBBranchFunc.class.getMethod("datBasesiteBatchInsert"), tmp);
					break;
				case 13: resTool.invoke(ResDBBranchFunc.class.getMethod("datBasesiteBatchUpdate"), tmp);
					break;
				case 14: ringTool.invoke(RingDBBranchFunc.class.getMethod("meterPerfBatchInsert"), tmp);
					break;
				case 15: ringTool.invoke(RingDBBranchFunc.class.getMethod("meterPerfBatchUpdate"), tmp);
					break;
				case 16: ringTool.invoke(RingDBBranchFunc.class.getMethod("powerPerfBatchInsert"), tmp);
					break;
				case 17: ringTool.invoke(RingDBBranchFunc.class.getMethod("powerPerfBatchUpdate"), tmp);
					break;
				default:break;
				}
				isuccess+=tmp.size();
			} catch (NoteException e) {
				isuccess+=tmp.size();
			} catch (Exception e) {
				ifailed+=tmp.size();
				e.printStackTrace();
			}

		}
		
		return "成功 "+isuccess+"条，失败 "+ifailed+"条";
		
	}
}
