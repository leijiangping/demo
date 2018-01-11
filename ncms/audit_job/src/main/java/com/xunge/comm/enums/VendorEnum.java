package com.xunge.comm.enums;

/**
 * 设备制造商
 * @author admin
 *
 */
public class VendorEnum extends BaseEnum<Object> {

	private static final long serialVersionUID = -4753760617915727214L;
	
	public static final VendorEnum self = new VendorEnum();

	/**
	 * 阿尔卡特
	 */
	public static final int ALCATEL = 0;
	/**
	 * 爱立信
	 */
	public static final int ERICSSON = 1;
	/**
	 * 伊塔泰尔
	 */
	public static final int ITATEL = 2;
	/**
	 * 摩托罗拉
	 */
	public static final int MOTOROLA = 3;
	/**
	 * 诺基亚
	 */
	public static final int NOKIA = 4;
	/**
	 * 北电
	 */
	public static final int NORTEL = 5;
	/**
	 * 西门子
	 */
	public static final int SIEMENS = 6;
	/**
	 * 中兴
	 */
	public static final int ZTE = 7;
	/**
	 * 华为
	 */
	public static final int HUAWEI = 8;
	/**
	 * 大唐
	 */
	public static final int DATANG = 9;
	/**
	 * 朗讯
	 */
	public static final int LUCENT = 10;
	/**
	 * 东信北邮
	 */
	public static final int EASTCOM = 11;
	/**
	 * 上海贝尔
	 */
	public static final int BELL = 12;
	/**
	 * 鼎桥
	 */
	public static final int TD_TECH = 200;
	/**
	 * 新邮通
	 */
	public static final int NEW_POSTCOM = 201;
	/**
	 * 烽火
	 */
	public static final int FIBERHOM = 202;
	/**
	 * 普天
	 */
	public static final int POTEVIO = 203;
	/**
	 * 艾默生
	 */
	public static final int AMS = 204;
	/**
	 * APC
	 */
	public static final int APC = 206;
	/**
	 * DUM
	 */
	public static final int DUM = 208;
	/**
	 * EPOWER
	 */
	public static final int EPOWER = 209;
	/**
	 * IMV
	 */
	public static final int IMV = 210;
	/**
	 * MGE
	 */
	public static final int MGE = 211;
	/**
	 * 爱克赛
	 */
	public static final int AKS = 212;
	/**
	 * 澳新
	 */
	public static final int AX = 213;
	/**
	 * 邦讯
	 */
	public static final int BX = 214;
	/**
	 * 北京四维安通
	 */
	public static final int BJSWAT = 215;
	/**
	 * 北京中创纪
	 */
	public static final int BJZCJ = 216;
	/**
	 * 贝斯特
	 */
	public static final int BEST = 217;
	/**
	 * 波力
	 */
	public static final int BL = 218;
	/**
	 * 春兰空调
	 */
	public static final int CLKT = 219;
	/**
	 * 大诚
	 */
	public static final int DC = 220;
	/**
	 * 动力源
	 */
	public static final int DLY = 221;
	/**
	 * 合广
	 */
	public static final int HG = 222;
	/**
	 * 康大奈特
	 */
	public static final int KDNT = 223;
	/**
	 * 力博特
	 */
	public static final int LBT = 224;
	/**
	 * 梅兰日兰
	 */
	public static final int MLRL = 225;
	/**
	 * 南方电子
	 */
	public static final int NFDZ = 226;
	/**
	 * 普天洲际
	 */
	public static final int PTZJ = 227;
	/**
	 * 侨虹
	 */
	public static final int QH = 228;
	/**
	 * 三菱
	 */
	public static final int SL = 229;
	/**
	 * 施威特克
	 */
	public static final int SWTK = 230;
	/**
	 * 首信
	 */
	public static final int SX = 231;
	/**
	 * 索克曼
	 */
	public static final int SKM = 232;
	/**
	 * 天河
	 */
	public static final int TH = 233;
	/**
	 * 通力环
	 */
	public static final int TLH = 234;
	/**
	 * 威科姆
	 */
	public static final int WKM = 235;
	/**
	 * 武汉洲际
	 */
	public static final int WHZJ = 236;
	/**
	 * 先控(APC)
	 */
	public static final int XK = 237;
	/**
	 * 新电源
	 */
	public static final int XDY = 238;
	/**
	 * 兴安
	 */
	public static final int XA = 239;
	/**
	 * 亚澳
	 */
	public static final int YA = 240;
	/**
	 * 移联信达
	 */
	public static final int YLXD = 241;
	/**
	 * 易达
	 */
	public static final int YD = 242;
	/**
	 * 意华宝
	 */
	public static final int YHB = 243;
	/**
	 * 意科
	 */
	public static final int YK = 244;
	/**
	 * 中创
	 */
	public static final int ZC = 245;
	/**
	 * 中达
	 */
	public static final int ZD = 246;
	/**
	 * 中恒
	 */
	public static final int ZH = 247;
	/**
	 * 洲际
	 */
	public static final int ZJI = 248;
	/**
	 * 珠江
	 */
	public static final int ZJ = 249;
	/**
	 * 高新兴
	 */
	public static final int GXX = 260;
	/**
	 * 中兴力维
	 */
	public static final int ZXLW = 261;
	
	private VendorEnum(){
		super.putEnum(ALCATEL, "0");
		super.putEnum(ERICSSON, "1");
		super.putEnum(ITATEL, "2");
		super.putEnum(MOTOROLA, "3");
		super.putEnum(NOKIA, "4");
		super.putEnum(NORTEL, "5");
		super.putEnum(SIEMENS, "6");
		super.putEnum(ZTE, "7");
		super.putEnum(HUAWEI, "8");
		super.putEnum(DATANG, "9");
		super.putEnum(LUCENT, "10");
		super.putEnum(EASTCOM, "11");
		super.putEnum(BELL, "12");
		super.putEnum(TD_TECH, "200");
		super.putEnum(NEW_POSTCOM, "201");
		super.putEnum(FIBERHOM, "202");
		super.putEnum(POTEVIO, "203");
		super.putEnum(AMS, "204");
		super.putEnum(APC, "206");
		super.putEnum(DUM, "208");
		super.putEnum(EPOWER, "209");
		super.putEnum(IMV, "210");
		super.putEnum(MGE, "211");
		super.putEnum(AKS, "212");
		super.putEnum(AX, "213");
		super.putEnum(BX, "214");
		super.putEnum(BJSWAT, "215");
		super.putEnum(BJZCJ, "216");
		super.putEnum(BEST, "217");
		super.putEnum(BL, "218");
		super.putEnum(CLKT, "219");
		super.putEnum(DC, "220");
		super.putEnum(DLY, "221");
		super.putEnum(HG, "222");
		super.putEnum(KDNT, "223");
		super.putEnum(LBT, "224");
		super.putEnum(MLRL, "225");
		super.putEnum(NFDZ, "226");
		super.putEnum(PTZJ, "227");
		super.putEnum(QH, "228");
		super.putEnum(SL, "229");
		super.putEnum(SWTK, "230");
		super.putEnum(SX, "231");
		super.putEnum(SKM, "232");
		super.putEnum(TH, "233");
		super.putEnum(TLH, "234");
		super.putEnum(WKM, "235");
		super.putEnum(WHZJ, "236");
		super.putEnum(XK, "237");
		super.putEnum(XDY, "238");
		super.putEnum(XA, "239");
		super.putEnum(YA, "240");
		super.putEnum(YLXD, "241");
		super.putEnum(YD, "242");
		super.putEnum(YHB, "243");
		super.putEnum(YK, "244");
		super.putEnum(ZC, "245");
		super.putEnum(ZD, "246");
		super.putEnum(ZH, "247");
		super.putEnum(ZJI, "248");
		super.putEnum(ZJ, "249");
		super.putEnum(GXX, "260");
		super.putEnum(ZXLW, "261");
		
		
	}
}
