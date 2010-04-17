
public enum QuasiId {
	// TODO: need to set these max gens to the correct value
	PRODUCT_ID(4, "`productID`", 0),
	PRICE(5,"`price`", 1),
	DEPT_ID(5, "`deptID`", 2),
	WEIGHT(4, "`weight`", 3),
	PRODUCT_YEAR(2, "`productYear`", 4),
	EXPIRE_YEAR(2, "`expireYear`", 5);
	
	final int maxGeneralization;
	final String dbName;
	final int position;
	
	QuasiId(int maxGeneralization, String dbName, int position) {
		this.maxGeneralization = maxGeneralization;
		this.dbName = dbName;
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
	public String getDBName() {
		return dbName;
	}
	
	public int getMaxGen() {
		return maxGeneralization;
	}
}
