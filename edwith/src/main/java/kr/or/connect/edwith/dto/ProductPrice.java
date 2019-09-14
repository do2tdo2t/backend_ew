package kr.or.connect.edwith.dto;

public class ProductPrice {
	private int productPriceId;
	private int productId;
	private String priceTypeName;
	private int price;
	private double discountRate;
	private String createDate;
	private String modifyDate;
	private String realPrice;
	private String discountRateStr;
	
	
	public String getDiscountRateStr() {
		return Integer.toString((int)(this.discountRate))+"%";
		
	}
	public void setDiscountRateStr() {
		this.discountRateStr = Integer.toString((int)(this.discountRate))+"%";
		
	}
	
	public void setRealPrice() {
		this.realPrice = this.getRealPrice();
	}
	
	public String getRealPrice() {
		int realPrice =(int) Math.round(this.price * discountRate / 100);
		char[] realPriceStr = Integer.toString(realPrice).toCharArray();
		StringBuffer realPriceStrBuffer = new StringBuffer();
		int cnt = 0;
		for(int i = realPriceStr.length-1 ;  i >=0  ; i-- ) {
			realPriceStrBuffer.append(realPriceStr[i]);
			cnt++;
			if(cnt %3 == 0 && cnt != realPriceStr.length+1) {
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
	
	@Override
	public String toString() {
		return "ProductPrice [productPriceId=" + productPriceId + ", productId=" + productId + ", priceTypeName="
				+ priceTypeName + ", price=" + price + ", discountRate=" + discountRate + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + "]";
	}
	
	
}
