package cn.blm.promise.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author jiaan.zhang@oracle.com
 * @date 12/10/2016 3:41 PM
 */
public class CodecUtil
{
	public static String md5(String value, String salt)
	{
		return DigestUtils.md5Hex(StringUtils.isNotBlank(salt) ? value + salt : value);
	}

	public static String md5(String value)
	{
		return md5(value, null);
	}

	public static String sha256(String value, String salt)
	{
		return DigestUtils.sha256Hex(StringUtils.isNotBlank(salt) ? value + salt : value);
	}

	public static String sha256(String value)
	{
		return sha256(value, null);
	}
}