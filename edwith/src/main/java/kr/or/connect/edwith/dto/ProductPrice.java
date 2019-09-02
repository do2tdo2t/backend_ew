package kr.or.connect.edwith.dto;

public class ProductPrice {
	private int id;
	private int categoryId;
	private String priceTypeName;
	private int price;
	private double discoutRate;
	private String createDate;
	private String modifyDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getPriceTypeName() {
		return priceTypeName;
	}
	public void setPriceTypeName(String priceTypeName) {
		this.priceTypeName = priceTypeName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public double getDiscoutRate() {
		return discoutRate;
	}
	public void setDiscoutRate(double discoutRate) {
		this.discoutRate = discoutRate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	@Override
	public String toString() {
		return "ProductPrice [id=" + id + ", categoryId=" + categoryId + ", priceTypeName=" + priceTypeName + ", price="
				+ price + ", discoutRate=" + discoutRate + ", createDate=" + createDate + ", modifyDate=" + modifyDate
				+ "]";
	}
	
}
