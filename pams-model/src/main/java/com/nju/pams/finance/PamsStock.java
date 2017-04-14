package com.nju.pams.finance;

public class PamsStock {
				
	private String symbolCode;		
	private int symbolType;
	private String symbolName;
	private int status;
    
    public PamsStock() {
    	
    }
    
    public PamsStock(String symbolCode, int symbolType, String symbolName, int status) {
    	this.symbolCode = symbolCode;
    	this.symbolType = symbolType;
    	this.symbolName = symbolName;
    	this.status = status;
    }
    
    public enum SymbolType { 
        SH(0),			//沪市	
        SZ(1);			//深市

        private final int index;
        private SymbolType(int index) {
            this.index = index;
        }
        public int getIndex() {
            return index;
        }
    }
    
    public enum Status { 
        Valid(0),			//有效	
        Invalid(1);			//无效

        private final int index;
        private Status(int index) {
            this.index = index;
        }
        public int getIndex() {
            return index;
        }
    }
    
	public String getSymbolCode() {
		return symbolCode;
	}

	public void setSymbolCode(String symbolCode) {
		this.symbolCode = symbolCode;
	}

	public String getSymbolName() {
		return symbolName;
	}

	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
	}

	public int getSymbolType() {
		return symbolType;
	}

	public void setSymbolType(int symbolType) {
		this.symbolType = symbolType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{PamsStock [")
		.append("symbolCode=" + symbolCode)
		.append(",symbolType=" + symbolType)
		.append(",symbolName=" + symbolName)
		.append(",status=" + status)
		.append("]}");
		return strBuf.toString();
	}
}
