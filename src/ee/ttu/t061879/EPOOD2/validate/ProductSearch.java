package ee.ttu.t061879.EPOOD2.validate;

import java.util.ArrayList;

public class ProductSearch {
	ArrayList<Integer> catalogs = new ArrayList<Integer>();
	
	String name = "",
		desc = "",
		minPrice = "",
		maxPrice = "",
		addedBy = "",
		minAdded = "",
		maxAdded = "",
		changedBy = "",
		minChanged = "",
		maxChanged = "";
	
	boolean ok = true,
		nameOk,
		descOk,
		minPriceOk,
		maxPriceOk,
		addedByOk,
		minAddedOk,
		maxAddedOk,
		changedByOk,
		minChangedOk,
		maxChangedOk;
	
	String nameError = "",
	descError = "",
	minPriceError = "",
	maxPriceError = "",
	addedByError = "",
	minAddedError = "",
	maxAddedError = "",
	changedByError = "",
	minChangedError = "",
	maxChangedError = "";
	
	public void addCatalog(int c){
		catalogs.add(c);
	}
	
	public ArrayList<Integer> getCatalogs() {
		return catalogs;
	}
	
	public boolean isOk() {
		return ok;
	}

	public boolean isNameOk() {
		return nameOk;
	}

	public boolean isDescOk() {
		return descOk;
	}

	public boolean isMinPriceOk() {
		return minPriceOk;
	}

	public boolean isMaxPriceOk() {
		return maxPriceOk;
	}

	public boolean isAddedByOk() {
		return addedByOk;
	}

	public boolean isMinAddedOk() {
		return minAddedOk;
	}

	public boolean isMaxAddedOk() {
		return maxAddedOk;
	}

	public boolean isChangedByOk() {
		return changedByOk;
	}

	public boolean isMinChangedOk() {
		return minChangedOk;
	}

	public boolean isMaxChangedOk() {
		return maxChangedOk;
	}

	public String getNameError() {
		return nameError;
	}

	public String getDescError() {
		return descError;
	}

	public String getMinPriceError() {
		return minPriceError;
	}

	public String getMaxPriceError() {
		return maxPriceError;
	}

	public String getAddedByError() {
		return addedByError;
	}

	public String getMinAddedError() {
		return minAddedError;
	}

	public String getMaxAddedError() {
		return maxAddedError;
	}

	public String getChangedByError() {
		return changedByError;
	}

	public String getMinChangedError() {
		return minChangedError;
	}

	public String getMaxChangedError() {
		return maxChangedError;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getMinAdded() {
		return minAdded;
	}

	public void setMinAdded(String minAdded) {
		this.minAdded = minAdded;
	}

	public String getMaxAdded() {
		return maxAdded;
	}

	public void setMaxAdded(String maxAdded) {
		this.maxAdded = maxAdded;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public String getMinChanged() {
		return minChanged;
	}

	public void setMinChanged(String minChanged) {
		this.minChanged = minChanged;
	}

	public String getMaxChanged() {
		return maxChanged;
	}

	public void setMaxChanged(String maxChanged) {
		this.maxChanged = maxChanged;
	}
}
