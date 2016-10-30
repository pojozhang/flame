package cn.blinkmind.promise.server.exception;

import org.springframework.http.HttpStatus;

/**
 * @author jiaan.zhang@oracle.com
 * @date 05/10/2016 10:27 PM
 */
public final class Errors
{
	public static final Error DOCUMENT_NAME_IS_BLANK = new Error(HttpStatus.BAD_REQUEST, 40010001, "document name is blank");

	public static final Error ARCHIVE_VERSION_IS_NULL = new Error(HttpStatus.BAD_REQUEST, 40011001, "archive version is null");
	public static final Error ARCHIVE_VERSION_IS_INVALID = new Error(HttpStatus.BAD_REQUEST, 40011002, "archive version is invalid");
	public static final Error ARCHIVE_BRANCH_IS_INVALID = new Error(HttpStatus.BAD_REQUEST, 40011003, "archive branch is invalid");
	public static final Error ARCHIVE_DOCUMENT_IS_NOT_SPECIFIED = new Error(HttpStatus.BAD_REQUEST, 40011004, "document is not specified");

	public static final Error MODULE_NAME_IS_BLANK = new Error(HttpStatus.BAD_REQUEST, 40012001, "module name is blank");

	public static final Error API_MODULE_IS_NOT_SPECIFIED = new Error(HttpStatus.BAD_REQUEST, 40013001, "module is not specified");
	public static final Error API_REQUEST_IS_NULL = new Error(HttpStatus.BAD_REQUEST, 40013002, "request is null");

	public static final Error REQUEST_URL_IS_BLANK = new Error(HttpStatus.BAD_REQUEST, 40014001, "request url is blank");
	public static final Error REQUEST_URL_IS_INVALID = new Error(HttpStatus.BAD_REQUEST, 40014002, "request url is invalid");

	public static final Error ACCOUNT_NAME_IS_BLANK = new Error(HttpStatus.BAD_REQUEST, 4004001, "account name is blank");
	public static final Error ACCOUNT_PASSWORD_IS_BLANK = new Error(HttpStatus.BAD_REQUEST, 4004002, "password is blank");

	public static final Error RESOURCE_NOT_FOUND = new Error(HttpStatus.NOT_FOUND, 40400001, "resource not found");
	public static final Error RESOURCE_ALREADY_EXISTS = new Error(HttpStatus.CONFLICT, 40900001, "resource already exists");
}
