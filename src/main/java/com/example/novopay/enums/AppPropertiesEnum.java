package com.example.novopay.enums;

public enum AppPropertiesEnum {

	/**
	 * Jwt token secret key app properties enum.
	 */
	JWT_TOKEN_SECRET_KEY("jwt.secret"),
	/**
	 * Encryption secret key app properties enum.
	 */
	ENCRYPTION_SECRET_KEY("encryption.secret"),
	/**
	 * Token expiry in secs key app properties enum.
	 */
	TOKEN_EXPIRY_IN_SECS_KEY("token.expiry.seconds"),
	/**
	 * Access token expiry in secs key app properties enum.
	 */
	ACCESS_TOKEN_EXPIRY_IN_SECS_KEY("jwt.access.token.expiration.seconds"),
	/**
	 * Refresh token expiry in secs key app properties enum.
	 */
	REFRESH_TOKEN_EXPIRY_IN_SECS_KEY("jwt.refresh.token.expiration.seconds");
	private String propName;

	AppPropertiesEnum(String propName) {
		this.propName = propName;
	}

	/**
	 * Gets prop name.
	 *
	 * @return the prop name
	 */
	public String getPropName() {
		return propName;
	}

	/**
	 * Sets prop name.
	 *
	 * @param propName
	 *            the prop name
	 */
	public void setPropName(String propName) {
		this.propName = propName;
	}
}
