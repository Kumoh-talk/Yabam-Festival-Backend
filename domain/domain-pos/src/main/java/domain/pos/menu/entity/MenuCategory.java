package domain.pos.menu.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuCategory {
	private Long menuCategoryId;
	private String menuCategoryName;
	private Long storeId;

	@Builder
	public MenuCategory(Long menuCategoryId, String menuCategoryName, Long storeId) {
		this.menuCategoryId = menuCategoryId;
		this.menuCategoryName = menuCategoryName;
		this.storeId = storeId;
	}
}
