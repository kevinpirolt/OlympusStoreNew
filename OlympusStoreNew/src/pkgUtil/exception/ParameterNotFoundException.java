package pkgUtil.exception;

import java.io.Serializable;

public class ParameterNotFoundException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterNotFoundException() {
		super();
	}

	public ParameterNotFoundException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ParameterNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ParameterNotFoundException(String arg0) {
		super(arg0);
	}

	public ParameterNotFoundException(Throwable arg0) {
		super(arg0);
	}
}
