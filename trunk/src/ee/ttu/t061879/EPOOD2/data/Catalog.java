package ee.ttu.t061879.EPOOD2.data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

public class Catalog {
	private int productCatalog,
		upperCatalog,
		structUnit,
		createdBy,
		updatedBy;
	private String name,
		description;
	private Date created,
		updated;
	private Collection<Catalog> subCatalogs;
	
	public Catalog() {
		subCatalogs = new ArrayList<Catalog>();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.productCatalog == ((Catalog)obj).getProductCatalog();
	}

	public Collection<Catalog> getSubCatalogs() {
		return subCatalogs;
	}

	public void addSubCatalog(Catalog c){
		if(c != null) subCatalogs.add(c);
	}
	
	public int getProductCatalog() {
		return productCatalog;
	}
	public void setProductCatalog(int productCatalog) {
		this.productCatalog = productCatalog;
	}
	public int getUpperCatalog() {
		return upperCatalog;
	}
	public void setUpperCatalog(int upperCatalog) {
		this.upperCatalog = upperCatalog;
	}
	public int getStructUnit() {
		return structUnit;
	}
	public void setStructUnit(int structUnit) {
		this.structUnit = structUnit;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}
