package domain.pos.menu.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuCategoryInfo {
	private Long menuCategoryId;
	private String menuCategoryName;
	private int menuCategoryOrder;

	@Builder
	public MenuCategoryInfo(Long menuCategoryId, String menuCategoryName, int menuCategoryOrder) {
		this.menuCategoryId = menuCategoryId;
		this.menuCategoryName = menuCategoryName;
		this.menuCategoryOrder = menuCategoryOrder;
	}
}
