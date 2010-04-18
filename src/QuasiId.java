// This enum is freaken hardcore!
public enum QuasiId
{
    /* Note: The way to find out the number of generalizations is the following:
     * Price: ceiling ( ln( maxValue ) / ln( 10 ) )
     * Dept_ID/Weight : ceiling ( ln( maxValue ) / ln ( 2 ) )
     */
    PRODUCT_ID(4, "`productID`", 0,9999), 
    PRICE(5, "`price`", 1, 99999), 
    DEPT_ID(6, "`deptID`", 2, 50), 
    WEIGHT(4, "`weight`", 3, 9),
    PRODUCT_YEAR(3, "`productYear`", 4, 2010), 
    EXPIRE_YEAR(3, "`expireYear`", 5, 2015);

    final int maxGeneralization;
    final String dbName;
    final int position;
    final int maxValue;

    QuasiId(int maxGeneralization, String dbName, int position, int maxValue)
    {
        this.maxGeneralization = maxGeneralization;
        this.dbName = dbName;
        this.position = position;
        this.maxValue = maxValue;
    }

    public int getPosition()
    {
        return position;
    }

    public String getDBName()
    {
        return dbName;
    }

    public int getMaxGen()
    {
        return maxGeneralization;
    }
}