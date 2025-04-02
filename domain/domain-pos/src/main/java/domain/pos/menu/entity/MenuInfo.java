package domain.pos.menu.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuInfo {
	private Long menuId;
	private int sortOrder;
	private String menuName;
	private int price;
	private String description;
	private String imageUrl;
	private boolean isSoldOut;

	@Builder
	public MenuInfo(Long menuId, int sortOrder, String menuName, int price, String description, String imageUrl,
		boolean isSoldOut) {
		this.menuId = menuId;
		this.sortOrder = sortOrder;
		this.menuName = menuName;
		this.price = price;
		this.description = description;
		this.imageUrl = imageUrl;
		this.isSoldOut = isSoldOut;
	}
}
