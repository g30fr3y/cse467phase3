
public class QuasiIdSelector {
	
	public QuasiIdSelector() {
		productId = false;
		price = false;
		weight = false;
		deptId = false;
		productYear = false;
		expireYear = false;
	}
	
	public QuasiIdSelector(QuasiIdSelector selector) {
		boolean[] values = selector.getSelected();
		
		int marker = 0;
		for (QuasiId id : QuasiId.values()) {
			this.setQuasiId(id, values[marker]);
			marker++;
		}
	}
	
	public void enableQuasiId(QuasiId id) {
		this.setQuasiId(id, true);
	}
	
	public void disAbleQuasiId(QuasiId id) {
		this.setQuasiId(id, false);
	}
	
	public boolean isEnabled(QuasiId id) {
		switch (id) {
			case DEPT_ID:
				return this.deptId;
			case EXPIRE_YEAR:
				return this.expireYear;
			case PRICE:
				return this.price;
			case PRODUCT_ID:
				return this.productId;
			case PRODUCT_YEAR:
				return this.productYear;
			case WEIGHT:
				return this.weight;
			default:
				return false;
		}	
	}
	
	private void setQuasiId(QuasiId id, boolean set) {
		switch (id) {
		case DEPT_ID:
			this.deptId = set;
			break;
		case EXPIRE_YEAR:
			this.expireYear = set;
			break;
		case PRICE:
			this.price = set;
			break;
		case PRODUCT_ID:
			this.productId = set;
			break;
		case PRODUCT_YEAR:
			this.productYear = set;
			break;
		case WEIGHT:
			this.weight = set;
			break;
		}		
	}
	
	private boolean[] getSelected() {
		boolean[] selected = new boolean[QuasiId.values().length];
		int marker = 0;
		for (QuasiId id : QuasiId.values()) {
			selected[marker] = isEnabled(id);
			marker++;
		}
		return selected;		
	}
	
	public boolean equals(QuasiIdSelector other) {
		if ( !(other instanceof QuasiIdSelector) ) {
			return false;
		} else {
			boolean[] values1 = this.getSelected();
			boolean[] values2 = other.getSelected();
			for (int i = 0; i < QuasiId.values().length; i++) {
				if ( !(values1[i] && values2[i]) ) {
					return false;
				}
			}
			return true;
		}
	}
	
	public String toString() {
		String output = "[ ";
		if (productId) {
			output += QuasiId.PRODUCT_ID + " ";
		}
		if (deptId) {
			output += QuasiId.DEPT_ID + " ";
		}
		if (price) {
			output += QuasiId.PRICE + " ";
		}
		if (weight) {
			output += QuasiId.WEIGHT + " ";
		}
		if (productYear) {
			output += QuasiId.PRODUCT_YEAR + " ";
		}
		if (expireYear) {
			output += QuasiId.EXPIRE_YEAR + " ";
		}
		output += "]";
		return output;
	}
	
	private boolean productId;
	private boolean price;
	private boolean weight;
	private boolean deptId;
	private boolean productYear;
	private boolean expireYear;
}
