
public enum QuasiId {
	// TODO: need to set these max gens to the correct value
	PRODUCT_ID(4, "`productID`"),
	PRICE(5,"`price`"),
	DEPT_ID(5, "`deptID`"),
	WEIGHT(4, "`weight`"),
	PRODUCT_YEAR(2, "`productYear`"),
	EXPIRE_YEAR(2, "`expireYear`");
	
	final int maxGeneralization;
	final String dbName;
	
	QuasiId(int maxGeneralization, String dbName) {
		this.maxGeneralization = maxGeneralization;
		this.dbName = dbName;
	}
	
	public String getDBName() {
		return dbName;
	}
	
	public int getMaxGen() {
		return maxGeneralization;
	}
}
