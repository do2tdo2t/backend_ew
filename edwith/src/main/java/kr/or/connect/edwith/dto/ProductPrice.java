package kr.or.connect.edwith.dto;

public class ProductPrice {
	private int productPriceId;
	private int productId;
	private String priceTypeName;
	private int price;
	private double discountRate;
	private String createDate;
	private String modifyDate;
	private String priceStr;
	private String discountRateStr;
	
	
	public String getDiscountRateStr() {
		return Integer.toString((int)(this.discountRate))+"%";
		
	}
	
	
	public void setDiscountRateStr() {
		this.discountRateStr = Integer.toString((int)(this.discountRate))+"%";	
	}
	
	public void setPriceStr() {
		this.priceStr = getPriceStr();
	}
	
	
	public String getPriceStr() {
		char[] priceStr = Integer.toString(this.price).toCharArray();
		StringBuffer realPriceStrBuffer = new StringBuffer();
		int cnt = 0;
		for(int i = priceStr.length-1 ;  i >=0  ; i-- ) {
			realPriceStrBuffer.append(priceStr[i]);
			cnt++;
			if(cnt %3 == 0 && cnt != priceStr.length+1) {
				realPriceStrBuffer.append(",");
			}
		}
		
		return realPriceStrBuffer.reverse().toString();
	}
	
	public int getProductPriceId() {
		return productPriceId;
	}
	public void setProductPriceId(int productPriceId) {
		this.productPriceId = productPriceId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
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
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
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
	
	
}
