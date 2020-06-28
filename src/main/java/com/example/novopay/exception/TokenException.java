package com.example.novopay.exception;

public class TokenException extends RuntimeException {
	private static final long serialVersionUID = -1977220980184789024L;

	/**
	 * Instantiates a new Token exception.
	 *
	 * @param message
	 *            the message
	 */
	public TokenException(final String message) {

		super(message);
	}

	/**
	 * Instantiates a new Token exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public TokenException(final Throwable cause) {

		super(cause);
	}

	/**
	 * Instantiates a new Token exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public TokenException(final String message, final Throwable cause) {

		super(message, cause);
	}

}
