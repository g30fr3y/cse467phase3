import java.util.Vector;


public class QuasiIdSelector {
	
	private QuasiIdSelector(QuasiId ... list) {
		this();
		for (QuasiId ids : list) {
			this.enableQuasiId(ids);
		}
	}
	
	private QuasiIdSelector() {
		productId = false;
		price = false;
		weight = false;
		deptId = false;
		productYear = false;
		expireYear = false;
	}
	
	public static QuasiIdSelector getQuasiIdSelector(QuasiId ... list) {
		return new QuasiIdSelector(list);
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
	
	public boolean equals(QuasiIdSelector other) {
		if ( (other != null) && (other instanceof QuasiIdSelector) ) {
			QuasiId[] ids1 = this.getEnabledIds();
			QuasiId[] ids2 = other.getEnabledIds();
			for (int i = 0; i < ids1.length; i++) {
				if ( ids1[i] != ids2[i] ) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		String output = "";
		QuasiId[] selected = this.getEnabledIds();
		for (QuasiId id : selected) {
			output += id + " ";
		}
		return "[ " + output + "]";
	}
	
	public QuasiId[] getEnabledIds() {
		Vector<QuasiId> enabled = new Vector<QuasiId>();
		for (QuasiId id : QuasiId.values()) {
			if (this.isEnabled(id)) {
				enabled.add(id);				
			}
		}
		
		QuasiId[] output = new QuasiId[enabled.size()];
		
		for (int i = 0; i < enabled.size(); i++) {
			output[i] = enabled.get(i);
		}
		
		return output;
	}

	private void enableQuasiId(QuasiId id) {
		this.setQuasiId(id, true);
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

	private boolean productId;
	private boolean price;
	private boolean weight;
	private boolean deptId;
	private boolean productYear;
	private boolean expireYear;
}
