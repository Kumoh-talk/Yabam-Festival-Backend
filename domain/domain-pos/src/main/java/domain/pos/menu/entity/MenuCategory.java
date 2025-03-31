package domain.pos.menu.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuCategory {
	private Long menuCategoryId;
	private String menuCategoryName;

	@Builder
	public MenuCategory(Long menuCategoryId, String menuCategoryName) {
		this.menuCategoryId = menuCategoryId;
		this.menuCategoryName = menuCategoryName;
	}
}
