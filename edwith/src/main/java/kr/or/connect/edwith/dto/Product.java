package kr.or.connect.edwith.dto;

public class Product {
	private int id;
	private int categoryId;
	private String description;
	private String content;
	private String event;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
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
		return "Product [id=" + id + ", categoryId=" + categoryId + ", description=" + description + ", content="
				+ content + ", event=" + event + ", createDate=" + createDate + ", modifyDate=" + modifyDate + "]";
	}

}
