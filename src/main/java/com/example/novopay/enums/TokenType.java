package com.example.novopay.enums;

public enum TokenType {
	/**
	 * Authorization token token type.
	 */
	AUTHORIZATION_TOKEN(0),
	/**
	 * Refresh token token type.
	 */
	REFRESH_TOKEN(1);

	private int tokenTypeId;

	/**
	 * Gets token type id.
	 *
	 * @return the token type id
	 */
	public int getTokenTypeId() {
		return tokenTypeId;
	}

	/**
	 * Sets token type id.
	 *
	 * @param tokenTypeId
	 *            the token type id
	 */
	public void setTokenTypeId(final int tokenTypeId) {
		this.tokenTypeId = tokenTypeId;
	}

	TokenType(final int tokenTypeId) {
		this.tokenTypeId = tokenTypeId;
	}
}
