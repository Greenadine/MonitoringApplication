package com.nerdygadgets.application.exception;

public class PowerShellScriptException extends Exception {

    public PowerShellScriptException(final Throwable parent) {
        super.initCause(parent);
    }

    @Override
    public void printStackTrace() {
        if (this.getCause() != null) {
            this.getCause().printStackTrace();
        } else {
            super.printStackTrace();
        }
    }
}
