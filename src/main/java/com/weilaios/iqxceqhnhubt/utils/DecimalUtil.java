package com.weilaios.iqxceqhnhubt.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 金额数据处理工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public final class DecimalUtil {
	private DecimalUtil() {
		/* EMPTY */
	}

	public static final BigDecimal ON_HUNDRED = new BigDecimal(100);
	private static final String PATTERN = "0.00";
	private static final String PATTERN_WITHOUT_SCALE = "0";
	private static final DecimalFormat FORMAT_WITHOUT_SCALE = new DecimalFormat(PATTERN_WITHOUT_SCALE);
	private static final DecimalFormat FORMAT = new DecimalFormat(PATTERN);
	private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆","伍", "陆", "柒", "捌", "玖" };
	private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元","拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾","佰", "仟" };
	private static final String CN_FULL = "整";
	private static final String CN_NEGATIVE = "负";
	 private static final int MONEY_PRECISION = 2;
	 private static final String CN_ZEOR_FULL = "零元" + CN_FULL;
	
	
	static {
		FORMAT.setRoundingMode(RoundingMode.HALF_UP);
	}

	/**
	 * 小数点后位数
	 */
	private static final int SCALE = 2;

	/**
	 * <p>转换double类型数据到BigDecimal类型.</p>
	 * 
	 * @param value Double类型的数据
	 * @return BigDecimal类型数据
	 * @author 
	 */
	public static BigDecimal format(final double value) {
		return (new BigDecimal(FORMAT.format(value)));
	}

	/**
	 * <p>转换long类型数据到BigDecimal类型.</p>
	 * 
	 * @param value Double类型的数据
	 * @return BigDecimal类型数据
	 * @author 
	 */
	public static BigDecimal format(final long value) {
		return (new BigDecimal(FORMAT.format(value)));
	}

	public static BigDecimal formatWithoutScale(final BigDecimal value) {
		return (new BigDecimal(FORMAT_WITHOUT_SCALE.format(value)));
	}

	/**
	 * <p>格式化BigDecimal类型的数值.</p>
	 * 
	 * @param value BigDecimal类型的数据
	 * @return BigDecimal类型数据
	 * @author 
	 */
	public static BigDecimal format(final BigDecimal value) {
		return value.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * <p>格式化BigDecimal类型的数值.</p>
	 * 
	 * @param value BigDecimal类型的数据
	 * @return BigDecimal类型数据
	 * @author 
	 */
	public static BigDecimal format(final BigDecimal value, int scale) {
		return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * <p>BigDecimal加法运算.</p>
	 * 
	 * @param d1 加数
	 * @param d2 加数
	 * @return BigDecimal类型
	 * @author 
	 */
	public static BigDecimal add(final BigDecimal d1, final BigDecimal d2) {
		return format(d1.add(d2));
	}

	/**
	 * <p>BigDecimal加法运算.</p>
	 * 
	 * @param d1 加数
	 * @param d2 加数
	 * @return BigDecimal类型
	 * @author 
	 */
	public static BigDecimal add(final BigDecimal d1, final BigDecimal d2, int scale) {
		return format(d1.add(d2), scale);
	}

	/**
	 * <p>多个BigDecimal类型的数据相加.</p>
	 * 
	 * @param v1
	 * @param vs
	 * @return
	 * @author 
	 */
	public static BigDecimal add(final BigDecimal v1, final BigDecimal... vs) {
		BigDecimal sum = BigDecimal.ZERO;
		for (final BigDecimal v : vs) {
			sum = add(sum, v);
		}
		return add(v1, sum);
	}

	/**
	 * <p>多个BigDecimal类型的数据连减.</p>
	 * 
	 * @param v1
	 * @param vs
	 * @return
	 * @author 
	 */
	public static BigDecimal subtract(final BigDecimal v1, final BigDecimal... vs) {
		final BigDecimal sum = add(BigDecimal.ZERO, vs);
		return subtract(v1, sum);
	}

	/**
	 * <p>BigDecimal减法运算.</p>
	 * 
	 * @param d1 运算数
	 * @param d2 运算数
	 * @return BigDecimal类型
	 * @author 
	 */
	public static BigDecimal subtract(final BigDecimal d1, final BigDecimal d2) {
		return format(d1.subtract(d2));
	}

	/**
	 * <p>BigDecimal减法运算.</p>
	 * 
	 * @param d1 运算数
	 * @param d2 运算数
	 * @return BigDecimal类型
	 * @author 
	 */
	public static BigDecimal subtract(final BigDecimal d1, final BigDecimal d2, final int scale) {
		return format(d1.subtract(d2), scale);
	}

	private static int compareBigDecimal(final BigDecimal v1, final BigDecimal v2) {
		return (format(v1).compareTo(format(v2)));
	}

	/**
	 * 
	 * <p>大于.</p>
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @author 
	 */
	public static boolean gt(final BigDecimal v1, final BigDecimal v2) {
		return compareBigDecimal(v1, v2) > 0;
	}

	/**
	 * <p>大于等于.</p>
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @author 
	 */
	public static boolean ge(final BigDecimal v1, final BigDecimal v2) {
		return compareBigDecimal(v1, v2) >= 0;
	}

	/**
	 * <p>等于.</p>
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @author 
	 */
	public static boolean eq(final BigDecimal v1, final BigDecimal v2) {
		return compareBigDecimal(v1, v2) == 0;
	}

	/**
	 * <p>小于.</p>
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @author 
	 */
	public static boolean lt(final BigDecimal v1, final BigDecimal v2) {
		return compareBigDecimal(v1, v2) < 0;
	}

	/**
	 * <p>小于等于.</p>
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @author 
	 */
	public static boolean le(final BigDecimal v1, final BigDecimal v2) {
		return compareBigDecimal(v1, v2) <= 0;
	}

	/**
	 * <p>不等于.</p>
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @author 
	 */
	public static boolean ne(final BigDecimal v1, final BigDecimal v2) {
		return compareBigDecimal(v1, v2) != 0;
	}

	/**
	 * <p>除法操作.</p>
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @author 
	 */
	public static BigDecimal divide(final BigDecimal v1, final BigDecimal v2) {
		return format(v1.divide(v2,BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * <p>除法操作.</p>
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @author 
	 */
	public static BigDecimal divide(final BigDecimal v1, final BigDecimal v2, final int scale) {
		return format(v1.divide(v2,scale,BigDecimal.ROUND_HALF_UP), scale);
	}
	
	public static BigDecimal divide5(final BigDecimal v1, final BigDecimal v2, final int scale) {
		return format(v1.divide(v2,scale,BigDecimal.ROUND_HALF_DOWN), scale);
	}

	/** 乘法操作 */
	public static BigDecimal multiply(final BigDecimal v1, final BigDecimal v2) {
		return format(v1.multiply(v2));
	}

	/** 乘法操作 */
	public static BigDecimal multiply(final BigDecimal v1, final BigDecimal v2, final int scale) {
		return format(v1.multiply(v2), scale);
	}
	
	/**
	 * 
	 * 数字转换为中文大写
	 * @author 
	 * @param numberOfMoney
	 * @return
	 */
	public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
		 StringBuffer sb = new StringBuffer();
		 // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
		 // positive.
		 int signum = numberOfMoney.signum();
		 // 零元整的情况
		 if (signum == 0) {
			 return CN_ZEOR_FULL;
		 }
		 //这里会进行金额的四舍五入
		 long number = numberOfMoney.movePointRight(MONEY_PRECISION)
				 .setScale(0, 4).abs().longValue();
		 // 得到小数点后两位值
		 long scale = number % 100;
		 int numUnit = 0;
		 int numIndex = 0;
		 boolean getZero = false;
		 // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
		 if (!(scale > 0)) {
			 numIndex = 2;
			 number = number / 100;
			 getZero = true;
		 }
		 if ((scale > 0) && (!(scale % 10 > 0))) {
			 numIndex = 1;
			 number = number / 10;
			 getZero = true;
		 }
		 int zeroSize = 0;
		   while (true) {
			 if (number <= 0) {
				 break;
			 }
			 // 每次获取到最后一个数
			 numUnit = (int) (number % 10);
			 if (numUnit > 0) {
				 if ((numIndex == 9) && (zeroSize >= 3)) {
					 sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
				 }
				 if ((numIndex == 13) && (zeroSize >= 3)) {
					 sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
				 }
				 sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				 sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				 getZero = false;
				 zeroSize = 0;
			 } else {
				 ++zeroSize;
				 if (!(getZero)) {
					 sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				 }
				 if (numIndex == 2) {
					if (number > 0) {
						sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
					}
				} else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				}
				getZero = true;
			}
			// 让number每次都去掉最后一个数
			number = number / 10;
			++numIndex;
		}
		// 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
		if (signum == -1) {
			sb.insert(0, CN_NEGATIVE);
		}
		// 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
		if (!(scale > 0)) {
			sb.append(CN_FULL);
		}
		return sb.toString();
	}
}

